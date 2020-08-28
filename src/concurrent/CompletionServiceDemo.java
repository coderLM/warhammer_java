package concurrent;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CompletionServiceDemo {
    public static void main(String[] args) {
        doAndGetM();
    }

    static int doAndGetM() {

// 创建线程池
        ExecutorService executor =
                Executors.newFixedThreadPool(3);
// 创建CompletionService
        CompletionService<Integer> cs = new
                ExecutorCompletionService<>(executor);
// 异步向电商S1询价
        cs.submit(() -> getPrice(0));
// 异步向电商S2询价
        cs.submit(() -> getPrice(1));
// 异步向电商S3询价
        cs.submit(() -> getPrice(2));
// 将询价结果异步保存到数据库
// 并计算最低报价
        int max = -1;
        for (int i = 0; i < 3; i++) {
            try {
                Integer r = cs.take().get();
                System.out.println("get r:::"+r);
                executor.execute(() -> save(r));
                max = Math.max(max, r);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return max;
    }

    static Integer getPrice(int index) {
        try {
            Thread.sleep((4-index) * 2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return index;
    }

    static void save(int v) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
