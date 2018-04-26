package com.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 生产者和消费者模式
 * @Author: ZhOu
 * @Date: 2018/4/26
 */
public class ProComSyncDemo {
    private static int count = 0;
    private static String LOCK = "lock";

    static class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (LOCK) {
                    if (count == 10) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    System.out.println(Thread.currentThread().getName() + "，生产者，当前有产品：" + count);
                    LOCK.notifyAll();
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
                synchronized (LOCK) {
                    if (count == 0) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count--;
                    System.out.println(Thread.currentThread().getName() + "，消费者，当前有产品：" + count);
                    LOCK.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(6);
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        for (int i = 0; i < 3; i++) {
            service.execute(producer);
            service.execute(consumer);
        }
        service.shutdown();
    }
}
