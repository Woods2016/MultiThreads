package com.atomic.sync.addr;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: synchronized 同步方法测试速度，耗时4585ms
 * @Author: ZhOu
 * @Date: 2018/5/15
 */
public class SyncDemo {
    private final static long MAX_VALUE = 100000000;
    private static int count = 0;
    private final static CountDownLatch countDownLatch = new CountDownLatch(5);

    private synchronized int add() {
        return ++count;
    }

    private synchronized int get() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        SyncDemo syncDemo = new SyncDemo();
        long startTime = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(5);
        MyTask myTask = new SyncDemo.MyTask(syncDemo, startTime);
        for (int i = 0; i < 5; i++) {
            service.submit(myTask);
        }
        countDownLatch.await();
        service.shutdown();

    }


    static class MyTask implements Runnable {
        private SyncDemo syncDemo;
        private long startTime;

        public MyTask(SyncDemo syncDemo, long startTime) {
            this.syncDemo = syncDemo;
            this.startTime = startTime;
        }

        @Override
        public void run() {
            long v = syncDemo.get();
            while (v < MAX_VALUE) {
                v = syncDemo.add();
            }
            System.out.println(Thread.currentThread().getName() + "消耗了：" + (System.currentTimeMillis() - startTime));
            countDownLatch.countDown();
        }
    }


}

