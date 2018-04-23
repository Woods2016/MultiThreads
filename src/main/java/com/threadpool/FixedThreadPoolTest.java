package com.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2018/4/15
 */
public class FixedThreadPoolTest {
    static class Mytask implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getId() + "开始执行任务，时间：" + System.currentTimeMillis());
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        Mytask mytask = new Mytask();
        for (int i = 0; i < 15; i++) {
            service.execute(mytask);
        }
    }
}
