package concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrintWithLock {
    public static void main(String[] args) {
//        new Thread(() -> printDouble(true), "single").start();
//        new Thread(() -> printDouble(false), "double").start();
        threeThreadRun();
    }

    static int num = 0;
    static int max = 200;

    private static synchronized void printDouble(boolean single) {
        while (num <= max) {
            if ((single && num % 2 != 0) || (!single && num % 2 == 0)) {
                System.out.println(Thread.currentThread().getName() + " : " + num++);
                PrintWithLock.class.notifyAll();
            } else {
                try {
                    PrintWithLock.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    //三个线程依次执行
    private static void threeThreadRun() {
        Thread t3 = new Thread(PrintWithLock::printThreadName, "t3");
        Thread t2 = new Thread(() -> {
            printThreadName();
//            t3.start();
        }, "t2");
        Thread t1 = new Thread(() -> {
            printThreadName();
//            t2.start();
        }, "t1");
//        t1.start();

        Executor singleThreadPool =  Executors.newSingleThreadExecutor();
        singleThreadPool.execute(t1);
        singleThreadPool.execute(t2);
        singleThreadPool.execute(t3);
    }

    private static void printThreadName() {
        System.out.println(Thread.currentThread().getName() + " running");
    }
}
