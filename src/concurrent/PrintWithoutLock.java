package concurrent;

public class PrintWithoutLock {


    public static void main(String[] args) {
        noLockTest();
    }

    static int num = 0;
    static volatile boolean flag = true;

    private static void noLockTest() {
        int max = 200;
        new Thread(() -> {
            while (num <= max) {
                if (flag) {
                    System.out.println(Thread.currentThread().getName() + " " + num++);
                    flag = false;
                }
            }
        }, "t1").start();
        new Thread(() -> {
            while (num <= max) {
                if (!flag) {
                    System.out.println(Thread.currentThread().getName() + " " + num++);
                    flag = true;
                }
            }
        }, "t2").start();
    }

    private static void noLockTest2() {
        int count = 200;
        Thread t1 = new Thread(() -> {
            for (; count > num; ) {
                if (!flag && (num == 0 || ++num % 2 == 0)) {
                    System.out.println(num);
                    flag = true;
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (; count > num; ) {
                if (flag && (++num % 2 != 0)) {
                    System.out.println(num);
                    flag = false;
                }
            }
        });
        t1.start();
        t2.start();
    }


}

