package com.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: ReentrantLock模式下的生产者消费者模式
 * @Author: ZhOu
 * @Date: 2018/4/27
 */
public class ProComConditionDemo {

    private final static ReentrantLock LOCK = new ReentrantLock();
    private final static Condition FULL = LOCK.newCondition();
    private final static Condition EMPTY = LOCK.newCondition();
    private static AtomicInteger count = new AtomicInteger(0);

    static class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                LOCK.lock();
                try {
                    if (count.get() == 10) {
                        FULL.await();
                    }
                    count.getAndIncrement();
                    System.out.println(Thread.currentThread().getName() + "，生产者，产品总量：" + count);
                    EMPTY.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    LOCK.unlock();
                }
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                LOCK.lock();
                try {
                    if (count.get() == 0) {
                        EMPTY.await();
                    }
                    count.decrementAndGet();
                    System.out.println(Thread.currentThread().getName() + "，消费者，产品余量：" + count);
                    FULL.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    LOCK.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(6);
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        for (int i = 0; i < 3; i++) {
            executor.execute(producer);
            executor.execute(consumer);

        }

        executor.shutdown();
    }
}
