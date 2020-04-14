package design;

import com.sun.corba.se.spi.orbutil.threadpool.Work;

/**
 * 代理模式
 * 目的：为其他对象提供一种代理以  控制  对这个对象的 访问。(控制访问)
 * 本质：在访问真实对象时引入一定程度的间接性，因为这种间接性，可以附加多种用途。
 */
public class ProxyDemo {
    public static void main(String[] args) {
        WorkerProxy proxy=new WorkerProxy();
        proxy.work();;
    }
}
abstract class Worker{
    public abstract void work();
}
class Coder extends Worker{
    @Override
    public void work() {
        System.out.println("coding");
    }
}
class WorkerProxy extends Worker{
    private Coder worker;
    @Override
    public void work() {
        if(worker==null){
            synchronized (this){
                if(worker==null){
                    worker=new Coder();
                }
            }
        }
        worker.work();
    }
}