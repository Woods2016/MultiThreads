package com.future;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Description: 测试Future、Callable和Runnable
 * @Author: ZhOu
 * @Date: 2017/3/7
 */
public class TestFuture {
    //测试Runnable通过Future返回为空
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<?> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("测试Future的返回");
            }
        });
        System.out.println("返回结果是：" + future.get());
        executorService.shutdown();
    }

    //测试Callable通过Future返回结果，如果想得到任务执行的结果或者是异常可对这个Future对象进行操作
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Future future = service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("测试Callable返回");
                return "result";
            }
        });
        System.out.println("返回结果是：" + future.get());
        service.shutdown();
    }


    //测试Future取消任务，调用cancel中断任务
    @Test
    public void test3() throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(() -> {
            try {
                while (true) {
                    System.out.println("开始执行任务");
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                System.out.println("InterruptedException 异常了");
                e.printStackTrace();
            }
            return "result";
        });

        //等待2秒之后，主动中断任务
        Thread.sleep(2000);
        System.out.println("返回的结果是：" + future.cancel(true));
        service.shutdown();
    }

    //用Callable时抛出异常，则Future什么也抓取不到
    @Test
    public void test4() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                throw new Exception("this is exception");
            }
        });
        System.out.println("返回的结果是：" + future.get());
        service.shutdown();
    }


}
