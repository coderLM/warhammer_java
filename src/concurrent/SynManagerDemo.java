package concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

public class SynManagerDemo {
    public static void main(String[] args) {
        int run = 2;
        switch (run) {
            case 0:
                //计数器：监测多任务是否到达某点
                CountDownLatchMethod();
                break;
            case 1:
                //栅栏：协调多任务在某个点等待，数满放行
                CyclicBarrierMethod();
                break;
            case 2:
                //阶段栅栏：栅栏的升级版，协调多任务在多个点等待，数满放行
                PhaserMethod();
                break;
            case 3:
                break;
        }

    }

    /**
     * 计数器
     */
    private static void CountDownLatchMethod() {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            //计数为0时，结束阻塞
            countDownLatch.await();
            System.out.println("main end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 阶段器
     */
    private static void PhaserMethod() {
        Phaser phaser = new Phaser() {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("onAdvance phase:::" + phase + " registeredParties:::" + registeredParties);
                //如果返回true则注销了 phase，不再有阶段拦截的功能且onAdvance也不再回调
                //所以这里最好是 phase==所需阶段数-1（从0开始）;
                return phase == 1;
            }
        };
        Runnable runnable = () -> {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " arrive 0");
                phaser.arriveAndAwaitAdvance();
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName() + " arrive 1");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " arrive end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        int threadCount = 3;
        for (int i = 0; i < threadCount; i++) {
            phaser.register();
            new Thread(runnable).start();
        }
    }

    /**
     * 栅栏
     */
    private static void CyclicBarrierMethod() {
        CyclicBarrier barrier = new CyclicBarrier(4);
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                //等待栅栏打开
                barrier.await();
                System.out.println("t1 end");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                barrier.await();
                System.out.println("t2 end");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                barrier.await();
                System.out.println("t3 end");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                barrier.await();
                System.out.println("t4 end");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
