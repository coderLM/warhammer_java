package base;

/**
 * 单例模式的正确打开方式
 */
public class Singleton {
    private volatile static Singleton instance;
    private static Object lock = new Object();

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
    private Singleton(){}
}
