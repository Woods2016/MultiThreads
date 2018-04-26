package com.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: Condition与ReentrantLock的关系，类似于synchronized和Object的关系
 * @Author: ZhOu
 * @Date: 2018/4/24
 */
public class ConditionTest {

    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static Condition condition = reentrantLock.newCondition();

    static class MyTask implements Runnable {

        @Override
        public void run() {
            try {
                reentrantLock.lock();
                //await()当前线程等待，同时释放锁，当其他线程执行signal时，线程会重新获得锁并继续执行
                condition.await();
                System.out.println("继续执行...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyTask myTask = new MyTask();
        Thread thread = new Thread(myTask);
        thread.start();
        Thread.sleep(1000);

        //主线程获取锁，然后唤醒等待中的线程
        reentrantLock.lock();

        //signal唤醒一个等待中的线程
        condition.signal();

        //释放锁
        reentrantLock.unlock();
    }
}
