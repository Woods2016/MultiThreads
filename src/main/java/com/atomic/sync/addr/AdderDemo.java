package com.atomic.sync.addr;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Description: LongAdder 测试，消耗2637ms
 * @Author: ZhOu
 * @Date: 2018/5/15
 */
public class AdderDemo {
    private static final LongAdder count = new LongAdder();
    private static CountDownLatch countDownLatch = new CountDownLatch(5);
    private static final int MAX_VALUE = 100000000;

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(5);
        MyTask myTask = new AdderDemo.MyTask(startTime);
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
            long v = count.sum();
            while (v < MAX_VALUE) {
                count.increment();
                v = count.sum();
            }
            System.out.println(Thread.currentThread().getName() + "消耗了：" + (System.currentTimeMillis() - startTime) + "\t v=" + v);
            countDownLatch.countDown();
        }
    }
}
