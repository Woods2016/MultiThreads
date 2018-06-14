package message.async;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description: 异步消息处理
 * @Author: ZhOu
 * @Date: 2018/5/7
 */
public class AsyncDemo1 {
    private CountDownLatch countDownLatch;
    private volatile boolean doneFinish;
    private volatile boolean sendFinish;
    private BlockingQueue<String> queue;
    private BufferedWriter bufferedWriter;

    public AsyncDemo1(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
        queue = new LinkedBlockingDeque<>();
        File file = new File("D:\\AsyncDemo.log");
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handle() {
        new Thread(() -> {
            while (!doneFinish) {
                String msg = queue.peek();
                if (msg != null) {
                    queue.poll();
                    try {
                        bufferedWriter.write(msg);
                        bufferedWriter.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (queue.isEmpty() && sendFinish) {
                    countDownLatch.countDown();
                    doneFinish = true;
                    break;
                }
            }
        }).start();
    }

    public void sendFinish() {
        sendFinish = true;
    }

    public void release() {
        System.out.println("录入完毕");
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (queue != null) {
            queue.clear();
            queue = null;
        }
    }

    public void setMsg(String msg) {
        if (msg != null && !msg.isEmpty()) {
            queue.add(msg);
        }
    }

    public static void main(String[] args) {
        CountDownLatch downLatch = new CountDownLatch(1);
        AsyncDemo1 asyncDemo1 = new AsyncDemo1(downLatch);

        asyncDemo1.handle();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String msg = scanner.next();
            if (msg.equals("exit")) {
                asyncDemo1.sendFinish();
                break;
            }
            asyncDemo1.setMsg(msg);
        }

        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        asyncDemo1.release();
        scanner.close();
    }


}
