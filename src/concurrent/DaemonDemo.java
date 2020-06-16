package concurrent;

public class DaemonDemo {
    public static void main(String[] args) {
        Thread t = new Thread(r2);
        t.setName("daemon");
        t.setDaemon(true);
        t.start();

        try {
            Thread.sleep(1000);
            System.out.println("main end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runnable r = new Runnable() {
            @Override
            public void run() {

            }
        };
    }

    static Runnable r2 = () -> {

        while (true) {
            try {
                Thread.sleep(10*1000);
                System.out.println(Thread.currentThread().getName() + " running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
