package concurrent;

public class PriorityDemo {
    public static void main(String[] args) {

        Thread h1 = new Thread(r1);
        Thread h2 = new Thread(r2);
        Thread h3 = new Thread(r1);
        Thread h4 = new Thread(r1);
        Thread l1 = new Thread(r2);
        l1.setName("Low");
        h1.setPriority(Thread.MAX_PRIORITY);
        h2.setPriority(Thread.MAX_PRIORITY);
        h3.setPriority(Thread.MAX_PRIORITY);
        h4.setPriority(Thread.MAX_PRIORITY);
        l1.setPriority(Thread.MIN_PRIORITY);
        h1.start();
        h2.start();
        h3.start();
        h4.start();
        l1.start();
    }

    static Runnable r1 = () -> {

        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };
    static Runnable r2 = () -> {
        for (int i = 0; i < 1000000; i++) {
            if (i % 10000 == 0) {
                System.out.println(Thread.currentThread().getName() + " running");
            }
        }
    };

}
