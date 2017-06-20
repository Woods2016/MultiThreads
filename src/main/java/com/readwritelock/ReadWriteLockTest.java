package com.readwritelock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description: 线程读写锁
 * @Author: ZhOu
 * @Date: 2017/6/20
 */
public class ReadWriteLockTest {
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = reentrantReadWriteLock.readLock();
    private static Lock writeLock = reentrantReadWriteLock.writeLock();
    private int value;
    private Object handleRead(Lock lock) throws InterruptedException {
        Integer var2;
        try {
            lock.lock();
            Thread.sleep(1000L);
            var2 = Integer.valueOf(this.value);
        } finally {
            lock.unlock();
        }
        return var2;
    }

    private void handleWrite(Lock lock, int value) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000L);
            this.value = value;
        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        final ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();
        Runnable readRunnable = new Runnable() {
            public void run() {
                try {
                    readWriteLockTest.handleRead(ReadWriteLockTest.readLock);
                } catch (InterruptedException var2) {
                    var2.printStackTrace();
                }

            }
        };
        Runnable writeRunnable = new Runnable() {
            public void run() {
                try {
                    readWriteLockTest.handleWrite(ReadWriteLockTest.writeLock, (new Random()).nextInt(10));
                } catch (InterruptedException var2) {
                    var2.printStackTrace();
                }

            }
        };

        int i;
        for(i = 0; i < 18; ++i) {
            (new Thread(readRunnable)).start();
        }

        for(i = 18; i < 20; ++i) {
            (new Thread(writeRunnable)).start();
        }

    }


}
