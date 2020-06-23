package concurrent;


import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class CompletableFutureDemo {
    static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
        //用两个异步(A、B)的结果去执行第三个任务(C)
        int run = 2;
        switch (run) {
            case 0:
                //原始写法
                useOld();
                break;
            case 1:
                //使用Future 代替回调处理器
                useFuture();
                break;
            case 2:
                //使用CompletableFuture 链接各任务
                useCompletableFuture();
                break;
            case 3:
                testCompletable();
                break;
        }


    }

    private static void testCompletable() {
        CompletableFuture future = syncMethod2().runAfterBoth(syncMethod2(), () -> {
            System.out.println("has done:::" + list.size());
        });
        try {
            System.out.println("main:::"+Thread.currentThread().getName());
            Thread.sleep(10000);
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    private static CompletableFuture<Integer> composeMethod(int v) {
        return CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return v + 5;
            }
        });
    }

    private static void useOld() {
        CallBack callBackA = CompletableFutureDemo::dealABResult;
        CallBack callBackB = CompletableFutureDemo::dealABResult;
        syncMethod0(callBackA);
        syncMethod0(callBackB);
    }

    private static void useFuture() {
        Future<Integer> futureA = syncMethod1();
        Future<Integer> futureB = syncMethod1();
        try {
            list.add(futureA.get());
            list.add(futureB.get());
            System.out.println("result:::" + doTaskC(list));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void useCompletableFuture() {
        CompletableFuture<Integer> cf = syncMethod2().thenCombine(syncMethod2(), (a, b) -> {
            System.out.println("do list add");
            list.add(a);
            list.add(b);
            return list;
        }).thenApply(CompletableFutureDemo::doTaskC);
        try {
            System.out.println("result:::" + cf.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    private static synchronized void dealABResult(int value) {
        list.add(value);
        if (list.size() == 2) {
            System.out.println("result:::" + doTaskC(list));
        }
    }

    private static int doTaskC(List<Integer> list) {
        System.out.println("doTaskC thread:::" + Thread.currentThread().getName());
        int count = 0;
        for (Integer integer : list) {
            count += integer;
        }
        return count;
    }

    /**
     * 回调实现
     *
     * @param callBack
     */
    private static void syncMethod0(CallBack callBack) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                callBack.onFinish(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }

    /**
     * Future实现
     *
     * @return
     */
    private static Future<Integer> syncMethod1() {
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        return futureTask;
    }

    /**
     * CompletableFuture 实现
     *
     * @return
     */
    private static CompletableFuture<Integer> syncMethod2() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("future thread:" + Thread.currentThread().getName());
                Thread.sleep(3000);
                System.out.println("future thread end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
    }

    private static Callable<Integer> callable = () -> {
        Thread.sleep(1000);
        return 1;
    };

    private interface CallBack {
        void onFinish(int value);
    }
}
