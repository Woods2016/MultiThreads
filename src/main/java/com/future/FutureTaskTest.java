package com.future;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Description: 简单测试FutureTask
 * @Author: ZhOu
 * @Date: 2017/3/7
 */
public class FutureTaskTest {

    //使用Thread方式执行
    @Test
    public void test1() {
        Callable<String> callable = getStringCallable();

        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();

        try {
            System.out.println("执行结果：" + futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    //使用ExecutorServer执行
    @Test
    public void test2() {
        Callable<String> callable = getStringCallable();
        FutureTask<String> futureTask = new FutureTask<>(callable);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(futureTask);
        try {
            System.out.println("执行结果：" + futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    private Callable<String> getStringCallable() {
        return () -> {
            System.out.println("start time=" + System.currentTimeMillis());
            System.out.println("sleep start");
            Thread.sleep(5000);
            System.out.println("sleep end");
            return "end time=" + System.currentTimeMillis();
        };
    }

}
