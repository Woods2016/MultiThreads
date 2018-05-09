package com.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Description: CompletableFuture
 * @Author: ZhOu
 * @Date: 2018/5/9
 */
public class CompletableFutureDemo1 {

    public static class MyTask implements Runnable {
        CompletableFuture<Integer> future;

        public MyTask(CompletableFuture<Integer> future) {
            this.future = future;
        }

        @Override
        public void run() {
            int result = 0;
            try {
                result = future.get() * future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(result);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        new Thread(new MyTask(future)).start();
        Thread.sleep(1000);
        future.complete(60);
    }
}
