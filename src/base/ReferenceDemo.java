package base;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceDemo {
    public static void main(String[] args) {
//        soft();
        weak();
//        phantom();
    }

    static void soft() {
        Car car = new Car("bmw");
        ReferenceQueue rq = new ReferenceQueue();
        SoftReference<Car> softReference = new SoftReference<>(car, rq);
        car=null;
        System.gc();
        System.runFinalization();
        System.out.println(softReference.get().name);
        System.out.println(rq.poll());
        //输出 bmw、null
    }

    static void weak() {
        Car car = new Car("bmw");
        ReferenceQueue rq = new ReferenceQueue();
        WeakReference<Car> weakReference = new WeakReference<>(car, rq);
        car=null;
        System.gc();
        System.runFinalization();
        System.out.println(weakReference.get());
        System.out.println(rq.poll()==weakReference);
        //输出 null、true
    }

    static void phantom() {
        // 创建一个字符串对象
        String str = new String("疯狂Java讲义");
        // 创建一个引用队列
        ReferenceQueue rq = new ReferenceQueue();
        // 创建一个虚引用，让此虚引用引用到"疯狂Java讲义"字符串
        PhantomReference pr = new PhantomReference(str, rq);
        // 切断str引用和"疯狂Java讲义"字符串之间的引用
        str = null;
        // 取出虚引用所引用的对象，并不能通过虚引用获取被引用的对象，所以此处输出null
        System.out.println(pr.get());  //①
        // 强制垃圾回收
        System.gc();
        System.runFinalization();
        // 垃圾回收之后，虚引用将被放入引用队列中
        // 取出引用队列中最先进入队列中的引用与pr进行比较
        System.out.println(rq.poll() == pr);   //②
        //输出 null、true
    }


    static class Car {
        String name;

        public Car(String n) {
            this.name = n;
        }
    }
}
