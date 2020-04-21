package design;

/**
 * 工厂模式
 * 定义：定义一个用于创建对象的接口，让子类决定实例化哪一个类。工厂方法使一个类的实例化延迟到其子类
 * 优点：相对于简单工厂模式，将工厂类抽象--->符合开闭原则
 * 缺点：
 * 1.增加了类的数量--每加一个品类就多加一个工厂类
 * 2.将选择的逻辑挪到了客户端
 */
public class FactoryDemo extends BaseDemo{
    @Override
    public void run() {
        CountFactory factoryAdd = new CountFactoryAdd();
        Count add = factoryAdd.create();
        System.out.println("+ result:" + add.getCount(1, 2));

        CountFactory factorySub = new CountFactorySub();
        Count sub = factorySub.create();
        System.out.println("- result:" + sub.getCount(3, 1));
    }


    interface CountFactory {
        Count create();
    }

    private class CountFactoryAdd implements CountFactory {

        @Override
        public Count create() {
            return new CountAdd();
        }
    }

    class CountFactorySub implements CountFactory {

        @Override
        public Count create() {
            return new CountSub();
        }
    }

    abstract class Count {
        public abstract int getCount(int start, int end);
    }

    class CountAdd extends Count {

        @Override
        public int getCount(int start, int end) {
            return start + end;
        }
    }

    class CountSub extends Count {

        @Override
        public int getCount(int start, int end) {
            return start - end;
        }
    }
}

