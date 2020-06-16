package concurrent;

public class InterruptedDemo {
    public static void main(String[] args) {
        interruptFun0();
//        interruptFun1();
    }

    private static void interruptFun1() {
        Runnable runnable = () -> {
            while (!Thread.interrupted()) {
                System.out.println("state:::" + Thread.currentThread().getState().toString());
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
        System.out.println("state:::" + thread.getState().toString());
    }

    private static void interruptFun0() {
        Runnable runnable = () -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("isInterrupted 1:::" + Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
                System.out.println("isInterrupted 2:::" + Thread.currentThread().isInterrupted());
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("state :::" + thread.getState());
//        thread.interrupt();
//        System.out.println("isInterrupted 0 :::" + thread.isInterrupted());
    }
}
