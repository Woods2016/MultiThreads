package com.define.threadfactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2018/2/22
 */
public class MyThreadFactory implements ThreadFactory {
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, "thread - " + (atomicInteger.getAndAdd(1)));
        return thread;
    }
}
