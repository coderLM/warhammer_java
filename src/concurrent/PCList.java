package concurrent;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者、消费者
 */
public class PCList {

    List<Integer> list = new ArrayList<>();
    int maxLen = 16;
    ReentrantLock lock = new ReentrantLock();
    Condition emptyCondition = lock.newCondition();
    Condition fullCondition = lock.newCondition();

    public static void main(String[] args) {
        PCList pcList = new PCList(10);

        for (int i = 0; i < 30; i++) {
            new Producer(pcList).start();
        }
        for (int i = 0; i < 10; i++) {
            new Consumer(pcList).start();
        }

    }

    public PCList(int len) {
        this.maxLen = len;
    }

    public void put(Integer value) throws InterruptedException {
        //暂时忽略 checkNull(value)
        lock.lockInterruptibly();
        try {
            while (list.size() >= maxLen) {
                System.out.println(getThreadName() + " put await");
                fullCondition.await();
                System.out.println(getThreadName() + " put wake");
            }
            enqueue(value);
        } finally {
            lock.unlock();
        }
    }

    public String getThreadName() {
        return Thread.currentThread().getName();
    }

    private static AtomicInteger producerIndex = new AtomicInteger(0);
    private static AtomicInteger consumerIndex = new AtomicInteger(0);

    public Integer take() throws InterruptedException {
        //暂时忽略 checkNull(value)
        lock.lockInterruptibly();
        try {
            while (list.size() == 0) {
                System.out.println(getThreadName() + " take await");
                emptyCondition.await();
                System.out.println(getThreadName() + " take wake");
            }
            return dequeue();
        } finally {
            lock.unlock();
        }
    }

    private void enqueue(Integer value) {
        this.list.add(value);
        emptyCondition.signal();
    }

    private Integer dequeue() {
        Integer result = list.remove(list.size() - 1);
        fullCondition.signal();
        return result;
    }


    static class Producer extends Thread {
        PCList list;

        public Producer(PCList list) {
            this.list = list;
            setName("producer" + producerIndex.getAndIncrement());
        }

        @Override
        public void run() {
            Random random = new Random();
            while (!Thread.interrupted()) {
                try {
                    list.put(random.nextInt(100));
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    static class Consumer extends Thread {
        PCList list;

        public Consumer(PCList list) {
            this.list = list;
            setName("consumer" + consumerIndex.getAndIncrement());
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    list.take();
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    private void blockingTest() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(16);
        //不阻塞；notFull返回true，full返回false；
        queue.offer("");
        //不阻塞；notFull返回true；full抛异常"Queue full"
        queue.add("");
        //阻塞，需要处理中断异常
        try {
            queue.put("");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //不阻塞，出队，notEmpty返回值，empty返回null
        queue.poll();
        //不阻塞，不出队，notEmpty返回值，empty返回null
        queue.peek();
        //阻塞，出队
        try {
            queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
