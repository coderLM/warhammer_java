import design.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String runClassName = "RemarkDemo";
        List<Class> list = new ArrayList<>();
        //根据条件创建对象
        list.add(SimpleFactoryDemo.class);//简单工厂--根据条件，返回对象
        list.add(FactoryDemo.class);//工厂模式--在简单工厂基础上抽象了factory，实现开闭原则，选择移到客户端
        list.add(StrategyDemo.class);//策略模式--在简单工厂基础上，包装对象，简化客户端调用
        list.add(AbstractFactoryDemo.class);//抽象工厂模式--在工厂模式基础上，一个工厂对应一族对象
        //包装对象
        list.add(ProxyDemo.class);//代理模式--控制访问
        list.add(FacadeDemo.class);//外观模式--简化调用
        list.add(DecorateDemo.class);//装饰模式--动态扩展
        //提取公共方法
        list.add(FormworkDemo.class);//模板模式
        //创建复杂对象
        list.add(BuilderDemo.class);//建造者模式
        //快速创建对象（克隆）
        list.add(OriginalDemo.class);//原形模式
        //对象的一对多通知
        list.add(ObserverDemo.class);//观察者模式
        //封装行为
        list.add(StateDemo.class);//状态模式
        //接口转换
        list.add(AdapterDemo.class);//适配器模式
        //保存对象状态以复用
        list.add(RemarkDemo.class);//备忘录模式
        try {
            for (Class c : list) {
                String pkgName = c.getPackage().getName();
                if (c.getName().equals(pkgName + "." + runClassName)) {
                    BaseDemo obj = (BaseDemo) c.newInstance();
                    obj.run();
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }


    }
}
