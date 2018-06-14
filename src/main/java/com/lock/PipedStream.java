package com.lock;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Random;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2018/4/28
 */
public class PipedStream {
    final static PipedInputStream inputStream = new PipedInputStream();
    final static PipedOutputStream outputStream = new PipedOutputStream();

    static {
        try {
            inputStream.connect(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Producer implements Runnable {
        @Override
        public void run() {
            int data = new Random().nextInt(100);
            System.out.println("写入的随机数是" + data);
            try {
                outputStream.write(data);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            try {
                int data = inputStream.read();
                System.out.println("获取到的数据是" + data);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    outputStream.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        PipedStream pipedStream = new PipedStream();
        new Thread(pipedStream.new Producer()).start();
        new Thread(pipedStream.new Consumer()).start();
    }
}
