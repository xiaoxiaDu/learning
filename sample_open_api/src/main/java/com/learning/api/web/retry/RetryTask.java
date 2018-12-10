package com.learning.api.web.retry;


import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 重试任务
 * Created by Administrator on 2018/10/15.
 */
public class RetryTask implements Delayed, Serializable {
    private static final long serialVersionUID = -1;
    private String uuid;

    private int index;// 间隔游标
    private int interval;// 间隔
    private long startTime;// 开始时间
    private Boolean success = false;// 是否成功

    private Class<? extends RetryAble> task;// 重试任务执行服务
    private String param;// 参数

    public RetryTask(){
        super();
    }

    public RetryTask(int interval, Class<? extends  RetryAble> task, String param){
        this(0, interval, task, param);
    }

    public RetryTask(int index, int interval, Class<? extends  RetryAble> task, String param){
        this.uuid = UUID.randomUUID().toString().replace("-", "");
        this.index = index;
        this.interval = interval;
        this.task = task;
        this.param = param;
    }

    public RetryTask update(int interval){
        RetryTask task = new RetryTask();
        task.uuid = this.uuid;
        task.index = this.index + 1;
        task.interval = interval;
        task.startTime = System.currentTimeMillis() + interval * 1000L;
        task.task = this.task;
        task.param = this.param;
        return task;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(startTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed delayed) {
        if (delayed == null) {
            return 0;
        }
        if (!(delayed instanceof RetryTask)) {
            return 0;
        }
        RetryTask otherTask = (RetryTask) delayed;
        long otherTaskStartTime = otherTask.getStartTime();
        return (int) (this.startTime - otherTaskStartTime);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Class<? extends RetryAble> getTask() {
        return task;
    }

    public void setTask(Class<? extends RetryAble> task) {
        this.task = task;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
