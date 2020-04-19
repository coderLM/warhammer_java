package design;

/**
 * 责任链模式
 * 场景：有多个对象可以处理同一个请求，具体哪个对象处理该请求由运行时刻自动确定
 * 目的：将请求的发送和处理解耦
 * 方法：拦截的类都实现统一接口。
 * 优点：
 * 1、降低耦合度。它将请求的发送者和接收者解耦。
 * 2、简化了对象。使得对象不需要知道链的结构。
 * 3、增强给对象指派职责的灵活性。通过改变链内的成员或者调动它们的次序，允许动态地新增或者删除责任。
 * 4、增加新的请求处理类很方便。
 * 缺点：
 * 1、不能保证请求一定被接收
 * 2、可能会造成循环调用
 * 3、系统性能将受到一定影响，而且在进行代码调试时不太方便。
 * 4、可能不容易观察运行时的特征，有碍于除错。
 * 参考：https://www.cnblogs.com/Java-Starter/p/9609835.html
 */
public class ChainDemo extends BaseDemo{
    @Override
    public void run() {
        Manager highManager = new HighManager(null);
        Manager commonManager= new CommonManager(highManager);
        commonManager.dealRequest(90);
        commonManager.dealRequest(900);
    }
    abstract class Manager{
        Manager superManager;
        public Manager(Manager manager){
            this.superManager=manager;
        }
        public abstract void dealRequest(int num);
    }
    class CommonManager extends Manager{

        public CommonManager(Manager manager) {
            super(manager);
        }

        @Override
        public void dealRequest(int num) {
            if(num<100){
                System.out.println("commonManager deal request:"+num);
            }else{
                superManager.dealRequest(num);
            }
        }
    }
    class HighManager extends Manager{

        public HighManager(Manager manager) {
            super(manager);
        }

        @Override
        public void dealRequest(int num) {
            if(num<1000){
                System.out.println("highManager deal request:"+num);
            }else{
                System.out.println("can't deal");
            }
        }
    }
}
