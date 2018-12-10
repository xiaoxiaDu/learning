package com.learning.test.delayed;

import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 延迟队列
 * Created by Administrator on 2018/10/15.
 */
public class DelayedQueueDemo {

    public static void main(String[] args) {
        Random rand = new Random(47);

        /**
         * DelayQueue类的主要作用：是一个无界的BlockingQueue，用于放置实现了Delayed接口的对象，
         * 其中的对象只能在其到期时才能从队列中取走。
         * 这种队列是有序的，即队头对象的延迟到期时间最长。
         * 注意：不能将null元素放置到这种队列中。
         */
        DelayQueue<DelayedTask> queue = new DelayQueue<DelayedTask>();

        /**
         Java通过Executors提供四种线程池，分别为：
         newCachedThreadPool 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
         newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
         newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
         newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
         */
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 20; i++) {
            int nextInt = rand.nextInt(5000);
            System.out.println("nextInt: " + nextInt);
            queue.put(new DelayedTask(nextInt));
        }

        Iterator<DelayedTask> iterator = queue.iterator();
        while (iterator.hasNext()){
            DelayedTask delayedTask = iterator.next();
            System.out.println("delayedTask: " + JSONObject.toJSONString(delayedTask));
        }


        System.out.println("--------------------------");
        queue.add(new DelayedTask.EndSentinel(5000, exec));

        exec.execute(new DelayedTaskConsumer(queue));

    }
}
