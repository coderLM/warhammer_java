package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * 使用信号量 实现简单的 对象池+限流器
 */
public class SemaphoreDemo {
    static ObjectPool<NamedPeople, String> pool = null;

    public static void main(String[] args) {
        try {
            pool = new ObjectPool<>(10, NamedPeople::newInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pool == null) return;
        for (int i = 0; i < 30; i++) {
            usePool();
        }
    }

    private static void usePool() {
        Runnable runnable = () -> {
            String name = pool.execute(namedPeople -> {
                try {
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return namedPeople.getName();
            });
            System.out.println("thread: " + Thread.currentThread().getName() + "  name is :" + name);
        };
        Thread t = new Thread(runnable);
        t.start();
    }

    static class ObjectPool<T, R> {
        List<T> objList = new Vector<>();
        Semaphore semaphore;

        ObjectPool(int size, Callable<T> initObj) throws Exception {
            for (int i = 0; i < size; i++) {
                objList.add(initObj.call());
            }
            semaphore = new Semaphore(size);
        }

        R execute(Function<T, R> function) {
            T obj = null;
            try {
                semaphore.acquire();
                obj = objList.remove(0);
                return function.apply(obj);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                objList.add(obj);
                semaphore.release();
            }
            return null;
        }
    }
}


class NamedPeople {
    static int id = 0;

    public static NamedPeople newInstance() {
        return new NamedPeople("" + id++);
    }

    private NamedPeople(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
