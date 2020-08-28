package concurrent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FutureDemo {
    public static void main(String[] args) {
        String input = "/users/sm-li/flutter";
        String keywords = "flutter";
//        Scanner in = new Scanner(System.in);
//        System.out.println("input dir");
//         input = in.nextLine();
//        System.out.println("input keywords");
//         keywords = in.nextLine();
        startCount();
        MatchCounter matchCounter = new MatchCounter(input, keywords);
        FutureTask task = new FutureTask<>(matchCounter);
        Thread thread = new Thread(task);
        thread.start();
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
        }
        printCount();
    }

    static long time;

    static void startCount() {
        time = System.currentTimeMillis();
    }

    static void printCount() {
        System.out.println("use time:::" + (System.currentTimeMillis() - time) + " ms");
    }
}

class MatchCounter implements Callable<Integer> {
    private final String path;
    private final String keywords;
    private static AtomicInteger taskCount = new AtomicInteger(0);

    MatchCounter(String path, String keywords) {
        this.path = path;
        this.keywords = keywords;
    }

    @Override
    public Integer call() {
        File[] files = new File(path).listFiles();
        List<FutureTask> futureTasks = new ArrayList<>();
        AtomicInteger count = new AtomicInteger();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                MatchCounter matchCounter = new MatchCounter(file.getPath(), keywords);
                FutureTask task = new FutureTask<>(matchCounter);
                futureTasks.add(task);
                Thread thread = new Thread(task);
                thread.start();
            } else {
                if (search(file)) count.getAndIncrement();
            }
        }
        taskCount.addAndGet(futureTasks.size());
        futureTasks.forEach(task -> {
            try {
                count.addAndGet((Integer) task.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
//        System.out.println("threadCount:::" + threadCount.toString());
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
