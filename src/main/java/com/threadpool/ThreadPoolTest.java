package com.threadpool;

import com.define.threadfactory.MyThreadFactory;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 线程池
 * @Author: ZhOu
 * @Date: 2018/2/22
 */
public class ThreadPoolTest {

    @Test
    public void test01() throws InterruptedException {
        //SynchronousQueue 核心线程数不受影响，线程池过期被回收。任务量超过最大线程数会抛出异常。
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 3, TimeUnit.SECONDS, new SynchronousQueue<>(), new MyThreadFactory());

        //LinkedBlockingDeque 没有大小限制，只重复使用核心线程，线程池中的数量等于核心线程数量，任务量超过最大线程数不会抛出异常。
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 3, TimeUnit.SECONDS, new LinkedBlockingDeque<>(),new MyThreadFactory());

        //LinkedBlockingDeque 限制大小，核心线程占满后放入到队列中，队列占满之后会创建新的线程，任务量超过最大线程数时会抛出异常。
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 3, TimeUnit.SECONDS, new LinkedBlockingDeque<>(3), new MyThreadFactory());
        Runnable myRunnable = () -> {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };
        executor.execute(myRunnable);
        executor.execute(myRunnable);
        executor.execute(myRunnable);
        System.out.println("---先开三个---\n");
        System.out.println("核心线程数" + executor.getCorePoolSize());
        System.out.println("线程池数" + executor.getPoolSize());
        System.out.println("队列任务数" + executor.getQueue().size());
        System.out.println("任务数" + executor.getTaskCount());
        System.out.println("已完成任务数" + executor.getCompletedTaskCount());
        executor.execute(myRunnable);
        executor.execute(myRunnable);
        executor.execute(myRunnable);
        System.out.println("\n---再开三个---\n");
        System.out.println("核心线程数" + executor.getCorePoolSize());
        System.out.println("线程池数" + executor.getPoolSize());
        System.out.println("队列任务数" + executor.getQueue().size());
        System.out.println("任务数" + executor.getTaskCount());
        System.out.println("已完成任务数" + executor.getCompletedTaskCount());
        Thread.sleep(8000);
        System.out.println("\n----8秒之后----\n");
        System.out.println("核心线程数" + executor.getCorePoolSize());
        System.out.println("线程池数" + executor.getPoolSize());
        System.out.println("队列任务数" + executor.getQueue().size());
        System.out.println("任务数" + executor.getTaskCount());
        System.out.println("已完成任务数" + executor.getCompletedTaskCount());
    }
}

