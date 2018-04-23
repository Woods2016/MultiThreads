package com.forkjoin;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2018/4/23
 */
public class RecursiveTaskDemo extends RecursiveTask<Integer> {

    private static final int MAX = 20;
    private int start;
    private int end;
    private int data[];

    public RecursiveTaskDemo(int start, int end, int[] data) {
        this.start = start;
        this.end = end;
        this.data = data;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if ((end - start) < MAX) {
            for (int i = start; i < end; i++) {
                sum += data[i];
            }
            System.out.println(Thread.currentThread().getName() + "正在执行任务...");
            return sum;
        } else {
            int middle = (start + end) / 2;
            RecursiveTaskDemo demo1 = new RecursiveTaskDemo(start, middle, data);
            RecursiveTaskDemo demo2 = new RecursiveTaskDemo(middle, end, data);
            demo1.fork();
            demo2.fork();
            return demo1.join() + demo2.join();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int arr[] = new int[100];
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            int rand = new Random().nextInt(100);
            arr[i] = rand;
            sum += rand;
        }

        System.out.println("获取值：" + sum);
        RecursiveTaskDemo recursiveTaskDemo = new RecursiveTaskDemo(0, arr.length, arr);
        Future<Integer> future = forkJoinPool.submit(recursiveTaskDemo);
        System.out.println("结果是：" + future.get());

        forkJoinPool.shutdown();
    }
}
