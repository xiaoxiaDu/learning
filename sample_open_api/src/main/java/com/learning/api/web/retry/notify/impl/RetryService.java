package com.learning.api.web.retry.notify.impl;

import com.alibaba.fastjson.JSON;
import com.learning.api.web.retry.RetryAble;
import com.learning.api.web.retry.RetryTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 重试任务服务
 * Created by Administrator on 2018/12/4.
 */
public class RetryService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final int[] DEFAULT_INTERVALS = new int[]{1 * 60, 5 * 60, 10 * 60, 30 * 60, 60 * 60, 2 * 3600, 5 * 3600, 10 * 600, 15 * 3600, 24 * 3600};

    private int[] intervals;
    private boolean start = false;

    private DelayQueue<RetryTask> delayQueue = new DelayQueue<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    public RetryService() {
        this(DEFAULT_INTERVALS);
    }

    public RetryService(int[] intervals) {
        this.intervals = intervals;
    }

    /**
     * 重试服务启动
     */
    public void start() {
        if (start) {
            return;
        }
        logger.info("重试服务启动...");
        start = true;
        Thread daeman = new Thread(new Runnable() {
            @Override
            public void run() {
                // 从库中找到还需要重试的任务
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        logger.info("查询需要重试的任务...");
                        List<RetryTask> oldTasks = new ArrayList<>();
                        // TODO start --- 测试添加一个
                        RetryTask taskTest = new RetryTask(0, intervals[0], null, "");
                        oldTasks.add(taskTest);
                        // TODO end ===
                        if (oldTasks == null || oldTasks.size() == 0) {
                            return;
                        }
                        for (RetryTask task : oldTasks) {
                            delayQueue.put(task);
                        }
                    }
                });

                // 等待重试任务
                while (true) {
                    try {
                        RetryTask task = delayQueue.take();
                        executor.submit(new Runnable() {
                            @Override
                            public void run() {
                                _retry(task);
                            }
                        });
                    } catch (InterruptedException e) {
                        logger.error("重试异常", e);
                    }
                }
            }
        });
        daeman.setName("retryService thread daeman");
        daeman.setDaemon(true);
        daeman.start();
    }

    /**
     * 重试
     *
     * @param task 队列中获取的任务
     */
    private void _retry(RetryTask task) {
        if (task == null) {
            return;
        }
        logger.info("task重试, uuid={}", task.getUuid());
        boolean flag = false;
        try {
            if (task.getTask() != null) {
                Class<? extends RetryAble> taskClass = task.getTask();
                RetryAble retryAble = taskClass.newInstance();
                flag = retryAble.retry(task.getParam());
            } else {
                flag = true;
            }
        } catch (Exception e) {
            logger.error("调用重试接口失败", e);
        }
        task.setSuccess(flag);
        // 任务执行结构判断
        if (task.getSuccess()) {
            removeTask(task);
            return;
        }
        // 执行任务失败了, 需要确认下一次执行任务
        RetryTask next = this._getNextTask(task);
        if (next == null) {
            logger.info("retryTask 的所有执行次数都已经执行完毕!, task={}", JSON.toJSONString(task));
            removeTask(task);
            return;
        }
        // 添加下一次执行任务回到队列
        logger.info("delayQueue.size={}", delayQueue.size());
        addTask(next);
        logger.info("delayQueue.size={}", delayQueue.size());
    }

    /**
     * 获取下一次任务执行
     *
     * @param task
     * @return
     */
    private RetryTask _getNextTask(RetryTask task) {
        int index = task.getIndex();
        if (index >= intervals.length - 1) {
            return null;
        }
        int nextInterval = intervals[index + 1];
        return task.update(nextInterval);
    }

    /**
     * 添加任务到队列中
     *
     * @param retryService
     * @param param
     */
    public void addNewTask(Class<? extends RetryAble> retryService, String param) {
        RetryTask retryTask = new RetryTask(intervals[0], retryService, param);
        this.addTask(retryTask);
    }

    /**
     * @param retryTask
     */
    public void addTask(RetryTask retryTask) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                delayQueue.put(retryTask);
                // 同时可以从这里将 retryTask 存库(使用uuid更新保存, 防止数据库中有重复数据)
                logger.info("_retry addTask delayQueue.size={}", delayQueue.size());
            }
        });
    }

    /**
     * @param retryTask
     */
    public void removeTask(final RetryTask retryTask) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                if (retryTask == null) {
                    return;
                }
                delayQueue.remove(retryTask);
                // 同时可以从这里将 retryTask 从库中删除(通过uuid)
                logger.info("_retry removeTask delayQueue.size={}", delayQueue.size());
            }
        });
    }
}
