package com.atomic;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description: AtomicReference保证操作对象的原子性
 * @Author: ZhOu
 * @Date: 2018/2/27
 */
public class AtomicReferenceTest {

    private static AtomicReference<Person> atomicReference;
    private static Person person;

    /**
     * 非原子操作，输出结果可能混乱
     */
    @Test
    public void test1() throws InterruptedException {
        person = new Person("Tom", 20);

        Thread t1 = new Thread(() -> {
            person.setAge(person.getAge() + 10);
            person.setName("Joy");
            System.out.println("t1 = " + person.toString());
        });

        Thread t2 = new Thread(() -> {
            person.setAge(person.getAge() + 1);
            person.setName("Ket");
            System.out.println("t2 = " + person.toString());
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("last = " + person.toString());
    }

    @Test
    public void test2() throws InterruptedException {
        person = new Person("Tom", 20);
        atomicReference = new AtomicReference<>(person);

        Thread t1 = new Thread(() -> {
            atomicReference.getAndSet(new Person("Joy", atomicReference.get().getAge() + 10));
            System.out.println("t1 = " + atomicReference.get().toString());
        });

        Thread t2 = new Thread(() -> {
            atomicReference.getAndSet(new Person("Ket", atomicReference.get().getAge() + 1));
            System.out.println("t2 = " + atomicReference.get().toString());
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("last = " + atomicReference.get().toString());
    }
}


@Data
@AllArgsConstructor
class Person {
    private String name;
    private int age;
}