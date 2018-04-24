package com.countdown;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2017/7/4
 */
public class CountDownLatchDemo implements Runnable {

    static CountDownLatch countDownLatch = new CountDownLatch(10);
    static CountDownLatchDemo downLatchDemo = new CountDownLatchDemo();

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(5000));
            System.out.println(Thread.currentThread().getName() + "检查完毕");
            //通知倒计时器，一个任务已经完成
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.submit(downLatchDemo);
        }
        //要求主线程等待所有线程执行完毕
        countDownLatch.await();
        System.out.println("发射");
        executorService.shutdown();
    }
}
