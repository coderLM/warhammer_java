package base;

/**
 * 单例模式的正确打开方式
 */
public class Singleton {
    //防止指令重排
    private volatile static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        //提高效率
        if (instance == null) {
            //同步
            synchronized (Singleton.class) {
                //为空就创建实例
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
