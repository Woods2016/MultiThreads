package com.cyclic;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Description: CyclicBarrier
 * @Author: ZhOu
 * @Date: 2018/4/15
 */
public class CyclicBarrierTest {

    public static class Soldier implements Runnable {
        private String name;
        private final CyclicBarrier cyclicBarrier;

        Soldier(String name, CyclicBarrier cyclicBarrier) {
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                cyclicBarrier.await();
                doWork();
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        private void doWork() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "任务完成");
        }
    }

    public static class BarrierRun implements Runnable {
        boolean flag;
        int count;

        BarrierRun(boolean flag, int count) {
            this.flag = flag;
            this.count = count;
        }

        @Override
        public void run() {
            if (flag) {
                System.out.println("司令：士兵" + count + "个完成任务");
            } else {
                System.out.println("司令：士兵" + count + "个报道");
                flag = true;
            }
        }
    }

    public static void main(String[] args) {
        int count = 10;
        boolean flag = false;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count, new BarrierRun(flag, count));
        System.out.println("开始集合");
        for (int i = 0; i < count; i++) {
            System.out.println(i + "士兵报道");
            Thread thread = new Thread(new Soldier("士兵" + i, cyclicBarrier));
            thread.start();
            //解散队伍，抛出异常
//            if(i==5){
//                thread.interrupt();
//            }

        }

    }
}
