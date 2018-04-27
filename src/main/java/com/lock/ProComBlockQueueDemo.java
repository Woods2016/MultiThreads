package com.lock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: BlockingQueue版生产者消费者模式
 * @Author: ZhOu
 * @Date: 2018/4/27
 */
public class ProComBlockQueueDemo {

    private static BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);
    private static int count = 0;

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
                    blockingQueue.put(1);
                    count++;
                    System.out.println(Thread.currentThread().getName() + "，生产者，总量：" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
                    blockingQueue.take();
                    count--;
                    System.out.println(Thread.currentThread().getName() + "，消费者，总量：" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
