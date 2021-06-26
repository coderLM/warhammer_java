package concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.*;

public class BlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);
        LongAdder adder = new LongAdder();
        AtomicInteger atomicInteger = new AtomicInteger();
        Thread.interrupted();
        Thread.currentThread().isInterrupted();
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        StampedLock stampedLock = new StampedLock();
        long stamp = stampedLock.tryOptimisticRead();
        if(!stampedLock.validate(stamp)){
            stamp=stampedLock.readLock();
            stampedLock.tryConvertToWriteLock(stamp);
        }
        stampedLock.unlock(stamp);


    }

    class Account {
        private int balance;
        private final Lock lock
                = new ReentrantLock();

        // 转账
        void transfer(Account tar, int amt) {
            while (true) {
                if (this.lock.tryLock()) {
                    try {
                        if (tar.lock.tryLock(3, TimeUnit.MILLISECONDS)) {
                            try {
                                this.balance -= amt;
                                tar.balance += amt;
                            } finally {
                                tar.lock.unlock();
                            }
                        }//if
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        this.lock.unlock();
                    }
                }//if
            }//while
        }//transfer
    }
}
