package concurrent;

import stream.StreamCreateDemo;
import test_util.TimeUtil;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class VolatileDemo {
    //不加 volatile 关键字              [1...,2...]
    // 加 volatile 关键字               同上
    //在add10K方法中使用同步             结果为 2...
//    private long count = 0;
    private AtomicLong count = new AtomicLong();

    private synchronized void add10K() {
        int idx = 0;
        while (idx++ < 10000000) {
            count.getAndAdd(1);
        }
    }

    public static long calc() {
        final VolatileDemo test = new VolatileDemo();
        // 创建两个线程，执行add()操作
        Thread th1 = new Thread(() -> {
            test.add10K();
        });
        Thread th2 = new Thread(() -> {
            test.add10K();
        });
        // 启动两个线程
        th1.start();
        th2.start();
        // 等待两个线程执行结束
        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("count:::" + test.count.get());
        return test.count.get();
    }

    public static void autoTest() {
        ArrayList<Integer> results = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            results.add((int) (calc() / 10000));
        }
        long result = results.stream().mapToInt(e -> e).sum();
        System.out.println("main end result:" + result / 100);
    }

    public static void main(String[] args) {

        TimeUtil.countFn(VolatileDemo::autoTest);
        //               result    time(ms)
        //无 volatile     1177       70
        //volatile        1171      35091
        // synchronized   2000       78
        //atomic          2000        9961
    }
}