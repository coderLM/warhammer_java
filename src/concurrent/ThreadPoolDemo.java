package concurrent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        String input = "/users/sm-li/flutter";
        String keywords = "flutter";
//        Scanner in = new Scanner(System.in);
//        System.out.println("input dir");
//         input = in.nextLine();
//        System.out.println("input keywords");
//         keywords = in.nextLine();
        startCount();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                5L, TimeUnit.SECONDS,
                new SynchronousQueue<>());
        MatchCounter2 matchCounter = new MatchCounter2(input, keywords,pool);
        Future<Integer> task = pool.submit(matchCounter);
        Thread printThread = new Thread(() -> {
            boolean done = false;
            while (!done) {
                System.out.println("activeCount:::" + Thread.activeCount());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    done = true;
                }
            }
        });
        printThread.start();
        try {
            System.out.println("result:::" + task.get());
            printThread.interrupt();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            printCount();
            System.out.println("pool largest size:::" + pool.getLargestPoolSize());
            System.exit(1);
        }

    }

    static long time;

    static void startCount() {
        time = System.currentTimeMillis();
    }

    static void printCount() {
        System.out.println("use time:::" + (System.currentTimeMillis() - time)+ " ms");
    }
}

class MatchCounter2 implements Callable<Integer> {
    private final String path;
    private final String keywords;
    private final ExecutorService pool;

    MatchCounter2(String path, String keywords, ExecutorService pool) {
        this.path = path;
        this.keywords = keywords;
        this.pool = pool;
    }

    @Override
    public Integer call() {
        File[] files = new File(path).listFiles();
        List<Future<Integer>> futureTasks = new ArrayList<>();
        AtomicInteger count = new AtomicInteger();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                MatchCounter2 matchCounter = new MatchCounter2(file.getPath(), keywords, pool);
                futureTasks.add(pool.submit(matchCounter));
            } else {
                if (search(file)) count.getAndIncrement();
            }
        }
        futureTasks.forEach(task -> {
            try {
                count.addAndGet(task.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        return count.get();
    }

    private boolean search(File file) {
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().contains(keywords)) return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}
