package design;

/**
 * Created by LiMeng on 2020/4/11.
 * 策略模式
 * 根据条件创建对象-->保存为成员变量-->方法中调用
 * 相对于简单（静态）工厂模式，更加简洁，客户端不用知道计算的父类
 */
public class StrategyDemo {
    public static void main(String[] args) {
        CalculationContext context = new CalculationContext("+");
        System.out.println("result:" + context.count(1,2));
    }
}

class CalculationContext {
    Calculation calculation;

    public CalculationContext(String type) {
        switch (type) {
            case "+":
                this.calculation = new CalculationAdd();
                break;
            case "-":
                this.calculation = new CalculationSub();
                break;
        }
    }
    public int count(int headNum,int endNum){
        return calculation.getCount(headNum,endNum);
    }
}

interface Calculation {
    int getCount(int headNum, int endNum);
}

class CalculationAdd implements Calculation {

    @Override
    public int getCount(int headNum, int endNum) {
        return headNum + endNum;
    }
}

class CalculationSub implements Calculation {

    @Override
    public int getCount(int headNum, int endNum) {
        return headNum - endNum;
    }
}