package concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    public static void main(String[] args) {
        LockDemo demo = new LockDemo();
        demo.run();
    }

    private void run() {
        Thread downThread = new Thread(downRunnable);
        Thread upThread = new Thread(upRunnable);
        upThread.suspend();
        downThread.start();
        upThread.start();
        try {
            Thread.sleep(10);
            downThread.interrupt();
//            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    Runnable downRunnable = new Runnable() {
        @Override
        public void run() {
            boolean run = true;
            do {

                lock.lock();
                try {
                    while (get() <= 0) condition.await();
                    down();
                    condition.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    run = false;
                } finally {
                    lock.unlock();
                }
            } while (run);
        }
    };
    Runnable upRunnable = new Runnable() {
        @Override
        public void run() {
            do {
                lock.lock();
                try {
                    while (get() >= 10) condition.await();
                    up();
                    condition.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            } while (true);
        }
    };
    int a = 0;

    public void up() {
        a += 3;
        System.out.println("up:::" + a);
    }

    public void down() {
        a--;
        System.out.println("down:::" + a);
    }

    public int get() {
        return a;
    }
}
