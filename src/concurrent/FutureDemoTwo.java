package concurrent;

import java.util.concurrent.*;

public class FutureDemoTwo {
    public static void main(String[] args) {
//        runnableTest();
//        runnableResultTest();
        futureTaskTest();
    }

    static void runnableTest() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(1);
            }
        };
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                3, 6, 3000,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100));
        Future future = executor.submit(runnable);
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    static void runnableResultTest() {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                3, 6, 3000,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100));
        Result result = new Result();
        ///其实就是runnable 执行完成后，直接返回了result
        ///可以不传result，写法如下，效果相同
        //  Future future = executor.submit(new TaskWithResult(result));
        /// future.get();//阻塞线程并返回null
        /// result.getLen();

        Future<Result> future = executor.submit(new TaskWithResult(result), result);
        try {
            System.out.println(future.get().getLen());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    static void futureTaskTest() {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1 + 1;
            }
        });
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(task);
        try {
            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    static void test() {
        FutureTask task = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
//                r1 = getPriceByS1();
//                save(r1);
                return null;
            }
        });
//        ...
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                3, 3,
                1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
        executor.submit(task);
//        executor.submit(task);
//        executor.submit(task);
    }

    private static class TaskWithResult implements Runnable {
        Result result;

        TaskWithResult(Result r) {
            this.result = r;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            result.setLen(30);
        }
    }

    private static class Result {
        int age = 5;
        int len = 10;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getLen() {
            return len;
        }

        public void setLen(int len) {
            this.len = len;
        }
    }
}
