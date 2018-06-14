package com.lock;

import java.util.concurrent.locks.StampedLock;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2018/5/14
 */
public class StampedLockDemo1 {

    private StampedLock stampedLock = new StampedLock();
    private int currentX, currentY;

    public void writeData(int x, int y) {
        long tmp = stampedLock.writeLock();

        try {
            this.currentX = x;
            this.currentY = y;
        } finally {
            stampedLock.unlockWrite(tmp);
        }
    }

    public int readData() {
        long tmp = stampedLock.tryOptimisticRead();
        int x = currentX, y = currentY;


        if (!stampedLock.validate(tmp)) {
            tmp = stampedLock.readLock();
            try {
                x = this.currentX;
                y = this.currentY;
            } finally {
                stampedLock.unlockRead(tmp);
            }
        }

        return x * y;
    }

}
