package com.daemon;

/**
 * @Description: 守护线程
 * @Author: ZhOu
 * @Date: 2017/6/20
 */
public class DaemonDemo {


    public static void main(String[] args) throws InterruptedException {
        ThreadDaemon t = new ThreadDaemon();
        t.setDaemon(true);
        t.start();
        Thread.sleep(2000L);
    }

    static class ThreadDaemon extends Thread {
        @Override
        public void run() {
            while (true) {
                System.out.println("线程执行");

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException var2) {
                    var2.printStackTrace();
                }
            }
        }
    }
}
