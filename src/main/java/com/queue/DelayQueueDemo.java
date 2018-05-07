package com.queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 延时队列，存放Delayed元素的无界阻塞队列
 * @Author: ZhOu
 * @Date: 2018/5/7
 */
public class DelayQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<DelayTask> delayQueue = new DelayQueue<>();
        for (int i = 0; i < 10; i++) {
            DelayTask delayTask = new DelayTask("Task" + i,
                    TimeUnit.MILLISECONDS,
                    new Random().nextInt(10) - 5L);
            delayQueue.add(delayTask);
            System.out.println(delayTask.toString());
        }

        System.out.println("\n#################################################################################\n");
        for (int i = 0; i < 10; i++) {
            //如果使用的是take方法，将会按到期顺序返回所有的元素
            DelayTask task = delayQueue.poll();

            //如果获取不到到期的延时任务，将会获取为null
            if (task != null) {
                System.out.println(task.toString());
            }
        }
    }
}

class DelayTask implements Delayed {

    private String name;
    private TimeUnit timeUnit;
    private Long executeTime;
    private Long delayTime;

    public DelayTask(String name, TimeUnit timeUnit, Long delayTime) {
        this.name = name;
        this.timeUnit = timeUnit;
        this.delayTime = delayTime;
        this.executeTime = System.currentTimeMillis() + timeUnit.toMillis(delayTime);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(executeTime - System.currentTimeMillis(), unit);
    }

    @Override
    public int compareTo(Delayed o) {
        if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
            return 1;
        } else if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "name:" + name + "\t delayTime:" + delayTime;
    }
}