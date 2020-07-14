package concurrent;

public class VolatileDemo {
    //不加 volatile 关键字  [1...,2...] 结果偏向左
    // 加 volatile 关键字               结果右移一点(CPU缓存有更新就会刷新到内存）
    //在add10K方法中使用同步              结果为 2...
    private volatile long count = 0;

    private void add10K() {
        int idx = 0;
        while(idx++ < 10000000) {
            count += 1;
        }
    }
    public static void calc() {
        final VolatileDemo test = new VolatileDemo();
        // 创建两个线程，执行add()操作
        Thread th1 = new Thread(()->{
            test.add10K();
        });
        Thread th2 = new Thread(()->{
            test.add10K();
        });
        // 启动两个线程
        th1.start();
        th2.start();
        // 等待两个线程执行结束
        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("count:::"+test.count);
    }

    public static void main(String[] args) {
        calc();
    }
}