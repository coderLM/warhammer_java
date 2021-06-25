package concurrent;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TTDemo {
    public static void main(String[] args) {
        TTDemo ttDemo = new TTDemo();
        ttDemo.startThread();
        try {
            Thread.sleep(3000);
            ttDemo.thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CountDownLatch countDownLatch = new CountDownLatch(2);
        try {
            countDownLatch.await();
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Semaphore semaphore = new Semaphore(3);
        try {
            semaphore.acquire();
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayBlockingQueue arrayBlockingQueue;

        AtomicInteger atomicInteger = new AtomicInteger(2);
        atomicInteger.getAndAdd(1);

        Thread thread;

        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        Condition condition = reentrantLock.newCondition();
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        condition.signal();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3){

        };
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

    private final Object lock = new Object();

    private void method() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    Thread thread;

    private void startThread() {
        thread = new Thread(this::method);
        thread.start();
    }

    private volatile Thread running;
    private void noLockSync(){
    }
}
