package com.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * @Description: RecursiveAction：没有返回值的ForkJoin
 * @Author: ZhOu
 * @Date: 2018/4/23
 */
public class RecursiveActionDemo extends RecursiveAction {
    private static final int MAX = 5;
    private int start;
    private int end;

    RecursiveActionDemo(int start, int end) {
        this.start = start;
        this.end = end;
    }


    @Override
    protected void compute() {
        if ((end - start) < MAX) {
            for (int i = start; i < end; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        } else {
            int middle = (start + end) / 2;
            RecursiveActionDemo action1 = new RecursiveActionDemo(start, middle);
            RecursiveActionDemo action2 = new RecursiveActionDemo(middle, end);
            action1.fork();
            action2.fork();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        RecursiveActionDemo actionDemo = new RecursiveActionDemo(0, 20);
        //提交执行任务
        forkJoinPool.submit(actionDemo);
        forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);
        forkJoinPool.shutdown();
    }
}
