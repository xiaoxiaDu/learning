package com.learning.test.delayed;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/10/15.
 * DelayedTask线程要实现Delayed接口的getDelay()和compareTo()方法，放入DelayQueue队列后，通过take()方法取出时，可根据compareTo方法制定的顺序来优先取出线程执行
 */
public class DelayedTask implements Delayed, Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final int delta;
    private final long trigger;
    protected static List<DelayedTask> sequence = new ArrayList<DelayedTask>();

    public DelayedTask(int delayInMilliseconds) {
        delta = delayInMilliseconds;
        System.out.println("delta: " + delta);
        trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta, TimeUnit.MILLISECONDS);
//        trigger = delta + 1;
        System.out.println("trigger: " + trigger);
        sequence.add(this);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long convert = unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
        System.out.println("convert: " +convert);
        return convert;
    }

    @Override
    public int compareTo(Delayed o) {
        DelayedTask that = (DelayedTask) o;
        if (this.trigger > that.trigger) {
            return 1;
        }
        if (this.trigger < that.trigger) {
            return -1;
        }
        return 0;
    }

    @Override
    public void run() {

    }

    public String summary() {
        return " id:" + id + "  delta:" + delta;
    }


    public static class EndSentinel extends DelayedTask {
        private ExecutorService exec;

        public EndSentinel(int delayInMilliseconds, ExecutorService exec) {
            super(delayInMilliseconds);
            this.exec = exec;
        }

        public void run() {
            for (DelayedTask delayedTask : sequence) {
                System.out.println(delayedTask.summary() + "  ");
            }
            System.out.print(' ');
            System.out.println(this + " Calling ShutdownNow()");
            exec.shutdownNow();
        }
    }


}

class DelayedTaskConsumer implements Runnable {

    private DelayQueue<DelayedTask> delayQueue;

    public DelayedTaskConsumer(DelayQueue<DelayedTask> delayQueue) {
        this.delayQueue = delayQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                delayQueue.take().run();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finished delayedtask consume!!!!!");
    }

}
