package concurrent;

import java.util.concurrent.*;

public class CollectLogDemo {
    BlockingQueue<Integer> queue;
    ExecutorService service;
    volatile boolean end = false;
    static final Integer endValue = -1;
    volatile boolean stop = false;

    public static void main(String[] args) {
        CollectLogDemo demo = new CollectLogDemo();
        demo.init();
        demo.start();
        demo.stop();
    }

    void init() {
        queue = new LinkedBlockingDeque<>(1000);
        service = new ThreadPoolExecutor(
                5, 5,
                3000, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>());
    }

    void start() {
        for (int i = 0; i < 5; i++) {
            service.submit(() -> {
                while (true) {
                    try {
                        Integer value = queue.take();
                        System.out.println("take:::" + value);
                        if (value.equals(endValue)) {
                            queue.put(endValue);
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
        }
        service.shutdown();
        new Thread(() -> {
            try {
                while (!stop) {
                    Thread.sleep(100);
                    queue.put((int) (Math.random() * 1000));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    void stop() {
        try {
            Thread.sleep(10 * 1000);
            stop=true;
            Thread.sleep(1000);
            queue.put(endValue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
