package com.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2018/5/2
 */
public class AtomicIntegerFiledUpdaterDemo {
    static class Person {
        int id;
        volatile int score;
    }

    static AtomicIntegerFieldUpdater<Person> person = AtomicIntegerFieldUpdater.newUpdater(Person.class, "score");
    static AtomicInteger allScore = new AtomicInteger(0);

    public static void main(String[] args) {
        final Person per = new Person();
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(() -> {
                if (Math.random() > 0.5) {
                    person.incrementAndGet(per);
                    allScore.incrementAndGet();
                }
            });
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("score=" + per.score);
        System.out.println("allScore=" + allScore);
    }
}
