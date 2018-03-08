package com.lock;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2018/2/24
 */
public class DeadLock {

    private static final String KA = "KA";
    private static final String KB = "KB";

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        Thread t1 = new Thread(() -> {
            synchronized (KA) {
                System.out.println("t1运行中。。。");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (KB) {
                    System.out.println("获取KB锁");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (KB) {
                System.out.println("t2运行中。。。");
                synchronized (KA) {
                    System.out.println("获取KA锁");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
