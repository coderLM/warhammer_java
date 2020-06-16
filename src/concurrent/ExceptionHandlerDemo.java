package concurrent;

public class ExceptionHandlerDemo {
    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println("running " + 1 / 0);
        Thread t = new Thread(runnable);
//        t.setUncaughtExceptionHandler((t1, e) -> {
//            System.out.println(t1.getName()+" has exception");
//            e.printStackTrace();
//        });
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName()+" has exception caught by default");
            }
        });
        t.start();

    }
}
