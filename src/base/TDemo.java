package base;

import com.sun.org.apache.xml.internal.security.Init;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiConsumer;

public class TDemo {
    public static void main(String[] args) {
        //可以不声明类型

        ConcurrentSkipListMap<String,Integer> skipListMap=new ConcurrentSkipListMap<>();
        skipListMap.put("b",1);
        skipListMap.put("a",2);
        skipListMap.put("c",3);

        skipListMap.forEach(new BiConsumer(){
            @Override
            public void accept(Object key, Object value) {
                System.out.println(key+" "+value);
            }
        });
        LinkedBlockingQueue linkedBlockingQueue;
        ConcurrentLinkedQueue queue;
        FutureTask task;
        AtomicInteger integer = new AtomicInteger(2);
        integer.getAndAdd(1);
        ReentrantLock reentrantLock;
        Semaphore semaphore;
        StringBuffer stringBuffer;
        ArrayBlockingQueue blockingQueue;
        Exchanger exchanger;

    }

    private static class Food{}
    private static class Fruit extends Food{}
    private static class Beat extends Food{}
}
