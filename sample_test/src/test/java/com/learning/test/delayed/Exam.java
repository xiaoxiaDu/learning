package com.learning.test.delayed;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by Administrator on 2018/10/15.
 */
public class Exam {

    /**
     * @param args void
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        int studentNumber = 20;
        CountDownLatch countDownLatch = new CountDownLatch(studentNumber + 1);
        DelayQueue<Student> students = new DelayQueue<>();
        Random random = new Random();
        for (int i = 0; i < studentNumber; i++) {
            students.put(new Student("student" + (i + 1), 30 + random.nextInt(120), countDownLatch));
        }
        // 主线程是老师, 一个老师监管多个子线程(延迟队列)学生
        Thread teacherThread = new Thread(new Teacher(students));
        students.put(new EndExam(students, 120, countDownLatch, teacherThread));// 最后强制结束的队列
        teacherThread.start();
        countDownLatch.await();
        System.out.println(" 考试时间到，全部交卷！");
    }

}

class Student implements Runnable, Delayed {

    private String name;
    private long workTime;
    private long submitTime;
    private boolean isForce = false;
    private CountDownLatch countDownLatch;

    public Student(String name, long workTime, CountDownLatch countDownLatch) {
        this.name = name;
        this.workTime = workTime;
//        this.submitTime = TimeUnit.NANOSECONDS.convert(workTime, TimeUnit.NANOSECONDS) + System.nanoTime();
        this.submitTime = TimeUnit.MILLISECONDS.convert(workTime, TimeUnit.MILLISECONDS) + System.currentTimeMillis();
        this.countDownLatch = countDownLatch;
    }

    @Override
    public int compareTo(Delayed o) {
        if (o == null || !(o instanceof Student)) {
            return 1;
        }
        if (o == this) {
            return 0;
        }
        Student s = (Student) o;
        if (this.workTime > s.workTime) {
            return 1;
        } else if (this.workTime == s.workTime) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public long getDelay(TimeUnit unit) {
//        return unit.convert(submitTime - System.nanoTime(), TimeUnit.NANOSECONDS);
        return unit.convert(submitTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        if (isForce) {
            System.out.println(name + " 交卷, 希望用时" + workTime + "分钟" + " ,实际用时 120分钟, ---> 强制交卷 " + submitTime);
        } else {
            System.out.println(name + " 交卷, 希望用时" + workTime + "分钟" + " ,实际用时 " + workTime + " 分钟 " + submitTime);
        }
        countDownLatch.countDown();
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean isForce) {
        this.isForce = isForce;
    }

}

class EndExam extends Student {

    private DelayQueue<Student> students;
    private CountDownLatch countDownLatch;
    private Thread teacherThread;

    public EndExam(DelayQueue<Student> students, long workTime, CountDownLatch countDownLatch, Thread teacherThread) {
        super("强制收卷", workTime, countDownLatch);
        this.students = students;
        this.countDownLatch = countDownLatch;
        this.teacherThread = teacherThread;
    }


    @Override
    public void run() {

        teacherThread.interrupt();// 老师的主线程中断
        Student tmpStudent;
        for (Iterator<Student> iterator2 = students.iterator(); iterator2.hasNext(); ) {
            tmpStudent = iterator2.next();
            tmpStudent.setForce(true);
            tmpStudent.run();
        }
        countDownLatch.countDown();
    }

}

class Teacher implements Runnable {

    private DelayQueue<Student> students;

    public Teacher(DelayQueue<Student> students) {
        this.students = students;
    }

    @Override
    public void run() {
        try {
            System.out.println(" test start");
            while (!Thread.interrupted()) { // 如果线程不是中断的
                students.take().run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
