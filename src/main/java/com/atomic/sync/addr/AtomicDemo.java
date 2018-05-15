package com.atomic.sync.addr;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 原子类测试速度，耗时2303ms
 * @Author: ZhOu
 * @Date: 2018/5/15
 */
public class AtomicDemo {
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private static CountDownLatch countDownLatch = new CountDownLatch(5);
    private static final long MAX_VALUE = 100000000;

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(5);
        MyTask myTask = new AtomicDemo.MyTask(startTime);
        for (int i = 0; i < 5; i++) {
            service.submit(myTask);
        }
        countDownLatch.await();
        service.shutdown();
    }

    static class MyTask implements Runnable {
        private long startTime;

        public MyTask(long startTime) {
            this.startTime = startTime;
        }

        @Override
        public void run() {
            long v = atomicInteger.get();
            while (v < MAX_VALUE) {
                v = atomicInteger.incrementAndGet();
            }
            System.out.println(Thread.currentThread().getName() + "消耗了：" + (System.currentTimeMillis() - startTime));
            countDownLatch.countDown();
        }
    }
}
