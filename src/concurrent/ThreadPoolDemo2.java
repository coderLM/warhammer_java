package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolDemo2 {
    public static void main(String[] args) {
//        runThreadPool();
        runThreadPool2();


    }

    private static void runFixedThreadPool() {
        Executors.newFixedThreadPool(3);
    }
    private static void runSingleThreadPool() {
        Executors.newSingleThreadExecutor();
    }

    private static void runThreadPool() {
        //有界阻塞队列 和 同步队列 容易引发reject异常
        ThreadPoolExecutor pool =
                new ThreadPoolExecutor(
                        5, 10,
                        60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2));
        Callable<Integer> callable = () -> {
            System.out.println("callable is running");
            Thread.sleep(3 * 1000);
            return 1;
        };
        List<Future<Integer>> list = new ArrayList<>();
        pool.prestartAllCoreThreads();
        //会抛出异常
        for (int i = 0; i < 13; i++) {
            Future<Integer> task = pool.submit(callable);
            list.add(task);
        }
        AtomicInteger count = new AtomicInteger();
        list.forEach(task -> {
            try {
                // 并不能一次性打印10个已经完成的线程结果，因为循环遍历是非原子操作
                // 且一般情况下，不能按照是否完成进行排序(这条的解决方案看 下一个方法)
                int single = task.get();
                count.addAndGet(single);
                System.out.println("single:::" + single);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        System.out.println("result:::" + count);
        pool.shutdown();
    }
    private static void runThreadPool2() {
        //有界阻塞队列 和 同步队列 容易引发reject异常
        ThreadPoolExecutor pool =
                new ThreadPoolExecutor(
                        5, 10,
                        60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2));
        //会按照完成顺序组织 完成队列，源码：BlockingQueue<Future<V>> completionQueue;
        ExecutorCompletionService service=new ExecutorCompletionService(pool);
        Callable<Integer> callable = () -> {
            System.out.println("callable is running");
            Thread.sleep(5 * 1000);
            return 1;
        };
        List<Future> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Future task= service.submit(callable);
            list.add(task);
        }
        for(int i=0;i<list.size();i++){
            try {
                int single = (int) service.take().get();
                System.out.println("single:::" + single);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        pool.shutdown();
    }
}
