package base;

/**
 * 单例模式 更简洁且的写法
 */

public class SingletonPro {
    private SingletonPro() {
    }

    private static class SingletonHolder {
        static SingletonPro singletonPro = new SingletonPro();
    }

    public static SingletonPro getInstance() {
        return SingletonHolder.singletonPro;
    }
}
