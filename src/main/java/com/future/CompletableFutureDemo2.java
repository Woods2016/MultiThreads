package com.future;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2018/5/9
 */
public class CompletableFutureDemo2 {
    public Integer calc(Integer para) {
        try {
            System.out.println("执行任务中....");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return para * para;
    }

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> calc(20));
        //get方法会阻塞等待执行结果
        System.out.println(future.get());
    }

    /**
     * 流式处理
     */
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        System.out.println("开始分配任务....");
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> calc(10))
                .thenApply((i) -> Integer.toString(i))
                .thenApply((s) -> "result=" + s)
                .thenAccept(System.out::println);
        future.get();
        System.out.println("任务执行完毕....");
    }

    /**
     * 异常处理exceptionally
     */
    @Test
    public void test3() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> (10 / 0))
                .exceptionally(ex -> {
                    System.out.println(ex.toString());
                    return 0;
                })
                .thenApply((i) -> "result=" + i)
                .thenAccept(System.out::println);
        future.get();
    }

    /**
     * thenCompose 处理后的结果给到另一个CompletableFuture
     */
    @Test
    public void test4() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> calc(2))
                .thenCompose((i) -> CompletableFuture.supplyAsync(() -> calc(i)))
                .thenApply(s -> "result=" + s)
                .thenAccept(System.out::println);
        future.get();
    }

    /**
     * thenCombine 把两个Future的结果合并
     */
    @Test
    public void test5() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> calc(2));
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> calc(3));

        CompletableFuture<Void> future = future1.thenCombine(future2, (i, j) -> i + j)
                .thenApply(s -> "result=" + s)
                .thenAccept(System.out::println);
        future.get();
    }

}
