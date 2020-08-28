package concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.DoublePredicate;

/***
 * 多线程递归(任务分解)
 */
public class ForkJoinDemo {
    public static void main(String[] args) {
        int len = 1000;
        double[] values = new double[len];
        for (int i = 0; i < len; i++) {
            values[i] = Math.random();
        }
        Counter counter = new Counter(values, 0, len, x -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return  x > 0.5;
        });
        boolean end=false;
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(counter);
        System.out.println("result:::" + counter.join());
    }
}

class Counter extends RecursiveTask<Integer> {
    public static int SINGLE = 100;
    double[] values;
    int from;
    int to;
    DoublePredicate filter;

    protected Counter(double[] values, int from, int to, DoublePredicate filter) {
        this.values = values;
        this.from = from;
        this.to = to;
        this.filter = filter;
    }

    @Override
    protected Integer compute() {
        if (to - from + 1 <= SINGLE) {
            int count = 0;
            for (int i = from; i < to; i++) {
                if (filter.test(values[i])) count++;
            }
            return count;
        } else {
            int middle = (to + from) / 2;
            Counter left = new Counter(values, from, middle, filter);
            Counter right = new Counter(values, middle, to, filter);
            //写法一
            invokeAll(left, right);//内部也只是 t2.fork()，t1.doInvoke()
            return left.join() + right.join();
            //写法二
//            left.fork();
//            return right.compute()+left.join();
            //写法三
//            left.fork();
//            right.fork();
//            return right.join()+left.join();
            //写法四 -- 错误的写法，可能会有大于4个的线程在跑
//            left.fork();
//            right.fork();
//            return left.join()+right.join();
        }
    }
}
