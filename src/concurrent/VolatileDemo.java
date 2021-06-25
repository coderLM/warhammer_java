package concurrent;

import test_util.TimeUtil;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileDemo {
    private volatile long count = 0;
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private void add10K(boolean t) {
        while (count < 10000000) {
            count++;
            atomicInteger.getAndIncrement();
        }
    }

    public static long calc() {
        final VolatileDemo test = new VolatileDemo();
        // 创建两个线程，执行add()操作
        Thread th1 = new Thread(() -> {
            test.add10K(true);
        });
        Thread th2 = new Thread(() -> {
            test.add10K(false);
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
        System.out.println("count:::" + test.count / 10000);
        System.out.println("atomicInteger:::" + test.atomicInteger.get());
        return test.count;
    }

    public static void autoTest() {
        ArrayList<Integer> results = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            results.add((int) (calc() / 10000));
        }
        long result = results.stream().mapToInt(e -> e).sum();
        System.out.println("main end result:" + result / 30);
    }

    public static void main(String[] args) {
        TimeUtil.countFn(VolatileDemo::autoTest);
    }
}