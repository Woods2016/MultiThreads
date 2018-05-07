package com.queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Description: 按优先级处理任务
 * @Author: ZhOu
 * @Date: 2018/5/7
 */
public class PriorityBlockingQueueDemo {

    public static void main(String[] args) {

        BlockingQueue<PriorityTask> queue = new PriorityBlockingQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.add(new PriorityTask(new Random().nextInt(50) + 10, "Task" + i));
        }

        for (int i = 0; i < 10; i++) {
            PriorityTask task = queue.poll();
            System.out.println(task.toString());
        }
    }
}
