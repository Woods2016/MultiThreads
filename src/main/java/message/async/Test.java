package message.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author: ZhOu
 * @Date: 2018/5/9
 */
public class Test {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        MyThread myThread = new MyThread();
        new Thread(myThread).start();
        new Thread(myThread).start();

        executorService.execute(new MyThread());
        executorService.execute(new MyThread2());
        executorService.execute(new MyThread2());

        System.out.println(3);
        System.out.println(4);

        executorService.shutdown();
    }


}

class MyThread implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class MyThread2 implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
