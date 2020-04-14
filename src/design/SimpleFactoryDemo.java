package design;

/**
 * Created by LiMeng on 2020/4/10.
 * 简单工厂模式
 * 根据条件返回不同的实例（往往拥有相同的父类）
 * 优点：封闭对象的创建过程
 */
public class SimpleFactoryDemo {
    public static void main(String[] args) {
        Count count = CountFactory.create("+");
        count.setHeadNum(2);
        count.setEndNum(3);
        System.out.println("result:"+count.getCount());
    }
}

class CountFactory {
    public static Count create(String type){
        Count count=null;
        switch (type) {
            case "+":
                count = new CountAdd();
                break;
            case "-":
                count = new CountSub();
                break;
        }
        return count;
    }
}

abstract class Count {
    int headNum;
    int endNum;

    public int getHeadNum() {
        return headNum;
    }

    public void setHeadNum(int headNum) {
        this.headNum = headNum;
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    public abstract int getCount();
}

class CountAdd extends Count {

    @Override
    public int getCount() {
        return headNum + endNum;
    }
}

class CountSub extends Count {

    @Override
    public int getCount() {
        return headNum - endNum;
    }
}