package com.threadlocal;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2018/3/4
 */
public class ThreadLocalTest {
    private static final ThreadLocal<Long> THL = new ThreadLocal<>();

    static void begin() {
        THL.set(System.currentTimeMillis());
    }

    static void end() {
        System.out.println(System.currentTimeMillis() - THL.get());
    }

    public static void main(String[] args) throws InterruptedException {
        begin();
        TimeUnit.SECONDS.sleep(1);
        end();
    }
}
