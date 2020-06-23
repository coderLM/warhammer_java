package test_util;

public class TimeUtil {
    static long time;

    public static void countFn(Runnable runnable) {
        startCount();
        runnable.run();
        printCount();
    }

    static void startCount() {
        time = System.currentTimeMillis();
    }

    static void printCount() {
        System.out.println("use time:::" + (System.currentTimeMillis() - time) + " ms");
    }
}
