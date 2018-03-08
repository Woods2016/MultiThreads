package com.lock;

import org.junit.Test;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2018/2/26
 */
public class SynchronizedTest {

    @Test
    public void test1(){
        synchronized (SynchronizedTest.class){
            System.out.println(123);
        }
    }
}
