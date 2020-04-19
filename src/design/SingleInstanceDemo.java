package design;

import sun.java2d.SunGraphics2D;
import sun.security.provider.Sun;

/**
 * 单例模式
 * 目的：创建单一实例
 */
public class SingleInstanceDemo extends BaseDemo {
    @Override
    public void run() {
        Slacker slacker = Slacker.getInstance();
        Hungry hungry = Hungry.getInstance();
    }


}

/**
 * 懒汉式
 */
class Slacker {
    private static Slacker instance;

    private Slacker() {
    }

    private static final Object lock = new Object();

    public static Slacker getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new Slacker();
                }
            }
        }
        return instance;
    }
}

/**
 * 饿汉式
 */
class Hungry {
    public static final Hungry instance = new Hungry();

    private Hungry() {
    }

    public static Hungry getInstance() {
        return instance;
    }
}