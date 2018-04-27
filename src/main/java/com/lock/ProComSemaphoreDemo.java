package com.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: Semaphore版生产者消费者模式
 * @Author: ZhOu
 * @Date: 2018/4/27
 */
public class ProComSemaphoreDemo {
    private final static Semaphore NOTFULL = new Semaphore(10);
    private final static Semaphore NOTEMPTY = new Semaphore(10);
    private final static Semaphore MUX = new Semaphore(1);
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

                try {
                    NOTFULL.acquire();
                    MUX.acquire();
                    count.incrementAndGet();
                    System.out.println(Thread.currentThread().getName() + "，生产者，总量：" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    MUX.release();
                    NOTEMPTY.release();
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

                try {
                    NOTEMPTY.acquire();
                    MUX.acquire();
                    count.decrementAndGet();
                    System.out.println(Thread.currentThread().getName() + "，消费者，总量：" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    MUX.release();
                    NOTFULL.release();
                }
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        for (int i = 0; i < 5; i++) {
            executor.execute(producer);
            executor.execute(consumer);
        }
        executor.shutdown();
    }
}
