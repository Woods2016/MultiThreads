package com.priority;

/**
 * @Description: 线程优先级
 * @Author: ZhOu
 * @Date: 2017/6/20
 */
public class ThreadPriority {
    public static void main(String[] args) {
        ThreadPriority.HighPriority highPriority = new ThreadPriority.HighPriority();
        ThreadPriority.LowPriority lowPriority = new ThreadPriority.LowPriority();
        highPriority.setPriority(10);
        lowPriority.setPriority(1);
        highPriority.start();
        lowPriority.start();
    }

    static class LowPriority extends Thread {
        int count = 0;
        @Override
        public void run() {
            synchronized(ThreadPriority.class) {
                do {
                    ++this.count;
                } while(this.count <= 10000);
                System.out.println("LowPriority完成任务");
            }
        }
    }

    static class HighPriority extends Thread {
        int count = 0;
        @Override
        public void run() {
            synchronized(ThreadPriority.class) {
                do {
                    ++this.count;
                } while(this.count <= 10000);

                System.out.println("HighPriority完成任务");
            }
        }
    }
}
