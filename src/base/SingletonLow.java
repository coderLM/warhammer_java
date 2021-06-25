package base;

public class SingletonLow {
    private SingletonLow() {
    }

    private static final SingletonLow instance = new SingletonLow();

    public static SingletonLow getInstance() {
        return instance;
    }
}
