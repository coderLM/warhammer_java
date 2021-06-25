import design.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 设计模式
 * 六大原则
 * 1.单一职责：类要职责单一
 * 2.接口隔离：接口要精简单一
 * 3.依赖倒置：面向接口编程
 * 4.里氏替换：不要破坏继承体系
 * 5.迪米特法则：降低耦合
 * 6.开闭原则：是对前面五大原则的总结，对扩展开放，对修改关闭。
 * 目的(面向对象四大好处)
 * 1.可维护
 * 2.可扩展
 * 3.可复用
 * 4.灵活性好
 * 总结
 * 1.用抽象构建框架，用实现扩展细节
 * 2.选择合适的设计模式，避免过度设计
 */
public class DesignMain {
    public static void main(String[] args) {
        String runClassName = "OriginalDemo";
        List<Class> list = new ArrayList<>();
        //--- 类设计 ---
        list.add(FormworkDemo.class);//模板模式--提取公共方法
        list.add(CompositeDemo.class);//组合模式--构建使用一致性的分层
        list.add(BridgeDemo.class);//桥梁模式--二维环境下，减少类的继承，分离抽象与实现
        //--- 创建对象 ---
        //根据条件创建一组对象
        list.add(SimpleFactoryDemo.class);//简单工厂--根据条件，返回对象
        list.add(FactoryDemo.class);//工厂模式--在简单工厂基础上抽象了factory，实现开闭原则，选择移到客户端
        list.add(AbstractFactoryDemo.class);//抽象工厂模式--在工厂模式基础上，一个工厂对应一族对象
        list.add(StrategyDemo.class);//策略模式--在简单工厂基础上，包装对象，简化客户端调用
        //创建复杂对象
        list.add(BuilderDemo.class);//建造者模式--创建复杂对象
        //--- 包装使用 ---
        list.add(ProxyDemo.class);//代理模式--控制访问
        list.add(FacadeDemo.class);//外观模式--简化调用
        list.add(DecorateDemo.class);//装饰模式--动态(多次)扩展
        list.add(AdapterDemo.class);//适配器模式--接口转换
        //--- 行为 ---
        list.add(IteratorDemo.class);//迭代器模式--分离集合对象的遍历行为
        list.add(VisitorDemo.class);//访问者模式--数据结构稳定的情况下，把处理从数据结构分离出来
        list.add(ExplainDemo.class);//解释器模式--新文法定义行为
        list.add(StateDemo.class);//状态模式--把不同的状态下放到对应的状态类
        //--- 恢复对象 ---
        list.add(RemarkDemo.class);//备忘录模式--对象状态的保存与恢复
        //--- 复用对象 ---
        list.add(SingleInstanceDemo.class);//单例模式
        list.add(OriginalDemo.class);//原形模式--快速创建对象（克隆）
        list.add(FlyweightDemo.class);//享元模式--大量细粒度对象的复用
        //--- 对象间通信 ---
        list.add(MediationDemo.class);//中介模式--多对象间复杂交互
        list.add(ObserverDemo.class);//观察者模式--消息的一对多通知
        //--- 任务处理 ---
        list.add(ChainDemo.class);//责任链模式--用一组单属性单调关联的对象处理消息
        list.add(CommandDemo.class);//命令模式--对请求进行封装、管理
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
