package com.schedule;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @Description: ScheduledExecutorService 线程定时任务
 * @Author: ZhOu
 * @Date: 2018/3/8
 */
public class ScheduledThreadTest {

    @Test
    public void test() throws InterruptedException {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("schedule-%d").build();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2, threadFactory);
        System.out.println(System.currentTimeMillis());
        executor.schedule(new MyTask(), 1, TimeUnit.SECONDS);
        //2s后开始执行，无论任务执行多久，后面每隔3秒执行一次任务。如果执行任务消耗时间小于等于3s，结果不受影响；大于3s，就会在执行完成之后继续执行任务
        executor.scheduleAtFixedRate(new MyTask(), 2, 3, TimeUnit.SECONDS);
        Thread.sleep(15000);
    }

    @Test
    public void delayTest() throws InterruptedException {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("delay-%d").build();
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(3, threadFactory);
        //2s后开始，任务执行完毕后，3s后再执行下一次任务。任务执行2s，等于每隔5s执行一次
        service.scheduleWithFixedDelay(new MyTask(), 2, 3, TimeUnit.SECONDS);
        Thread.sleep(20000);
    }

    class MyTask implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                System.out.println(String.format("%s %d, time=%d ", Thread.currentThread().getName(), Thread.currentThread().getId(), System.currentTimeMillis()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
