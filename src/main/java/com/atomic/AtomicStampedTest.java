package com.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2018/5/2
 */
public class AtomicStampedTest {

    /**
     * 不加时间戳，会有ABA的问题
     */
    @Test
    public void test1() {
        AtomicReference<Integer> money = new AtomicReference(19);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                while (true) {
                    Integer m = money.get();
                    if (m < 20) {
                        if (money.compareAndSet(m, m + 20)) {
                            System.out.println("余额小于20，充值成功，余额：" + money.get());
                            break;
                        }
                    } else {
                        System.out.println("余额大于20，不能充值");
                        break;
                    }

                }
            }).start();
        }

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                while (true) {
                    Integer m = money.get();
                    if (m > 10) {
                        if (money.compareAndSet(m, m - 10)) {
                            System.out.println("消费10元，余额：" + money.get());
                            break;
                        }
                    } else {
                        System.out.println("不足10元，无法消费");
                        break;
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 添加版本号，可以防止ABA的问题
     */
    @Test
    public void test2() {
        final AtomicStampedReference<Integer> money = new AtomicStampedReference<>(19, 0);
        for (int i = 0; i < 5; i++) {
            final int t = money.getStamp();
            new Thread(() -> {
                while (true) {
                    int m = money.getReference();
                    if (m < 20) {
                        if (money.compareAndSet(m, m + 20, t, t + 1)) {
                            System.out.println("余额小于20，充值成功，余额：" + money.getReference());
                            break;
                        }
                    } else {
                        System.out.println("余额大于20元，不能充值");
                        break;
                    }
                }
            }).start();
        }

        new Thread(() -> {
            for (int j = 0; j < 10; j++) {
                while (true) {
                    int m = money.getReference();
                    int t = money.getStamp();
                    if (m > 10) {
                        if (money.compareAndSet(m, m - 10, t, t + 1)) {
                            System.out.println("消费10元，余额：" + money.getReference());
                            break;
                        }
                    } else {
                        System.out.println("余额小于10元，无法消费");
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
