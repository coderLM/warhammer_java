package design;

/**
 * 抽象工厂方法
 * <p>
 * 参考：
 * https://blog.csdn.net/jason0539/article/details/44976775
 * https://www.cnblogs.com/hardy-test/p/10588720.html
 */
public class AbstractFactoryDemo extends BaseDemo {
    @Override
    public void run() {
        Factory factory = new FactoryA();//只需更改工厂，就能获得一个品类的对象
        Components engine = factory.productEngine();
        Components aircondition = factory.productAircondition();
        engine.run();
        aircondition.run();

    }

    interface Components {
        void run();
    }

    abstract class Engine implements Components {
    }

    abstract class Aircondition implements Components {
    }

    class EngineA extends Engine {

        @Override
        public void run() {
            System.out.println("is EngineA");
        }
    }

    class EngineB extends Engine {

        @Override
        public void run() {
            System.out.println("is EngineB");
        }
    }

    class AirconditionA extends Aircondition {

        @Override
        public void run() {
            System.out.println("is AirconditionA");
        }
    }

    class AirconditionB extends Aircondition {
        @Override
        public void run() {
            System.out.println("is AirconditionB");
        }
    }


    interface Factory {
        Engine productEngine();

        Aircondition productAircondition();
    }

    class FactoryA implements Factory {
        @Override
        public Engine productEngine() {
            return new EngineA();
        }

        @Override
        public Aircondition productAircondition() {
            return new AirconditionA();
        }
    }

    class FactoryB implements Factory {
        @Override
        public Engine productEngine() {
            return new EngineB();
        }

        @Override
        public Aircondition productAircondition() {
            return new AirconditionB();
        }
    }

}
