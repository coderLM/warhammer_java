package concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class BlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<Integer>  blockingQueue=new ArrayBlockingQueue<>(10);
        LongAdder adder=new LongAdder();
        AtomicInteger atomicInteger = new AtomicInteger();


    }
}
