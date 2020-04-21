package design;

import java.util.ArrayList;
import java.util.List;

/**
 * 访问者模式
 * 场景：需要对一个对象结构中的对象进行很多不同的并且不相关的操作，而需要避免让这些操作“污染”这些对象的类时使用
 * 方法：在被访问的类里面添加一个对外提供接待访问者的接口
 * 目的：数据结构稳定的情况下，把处理从数据结构分离出来
 * 优点：
 * 1.符合单一职责原则
 * 2.优秀的扩展性
 * 3.灵活性非常高
 * 缺点：
 * 1.具体元素对访问者公布细节，也就是说访问者关注了其他类的内部细节，这是迪米特法则所不建议的
 * 2.具体元素变更比较困难
 * 3.违背了依赖倒转原则。访问者依赖的是具体元素，而不是抽象元素
 * 4.复杂度高，非必须，不使用
 */
public class VisitorDemo extends BaseDemo {
    @Override
    public void run() {
        Visitor visitorM = new VisitorM();
        Visitor visitorN = new VisitorN();
        ObjectStructure objectStructure = new ObjectStructure();
        Element elementA= new ElementA();
        Element elementB= new ElementB();
        objectStructure.add(elementA);
        objectStructure.add(elementB);
        objectStructure.bind(visitorM);
        objectStructure.bind(visitorN);
    }

    abstract class Visitor {
        public abstract void visitElementA(ElementA elementA);

        public abstract void visitElementB(ElementB elementB);
    }

    abstract class Element {
        public abstract void setVisitor(Visitor visitor);
    }

    class VisitorM extends Visitor {

        @Override
        public void visitElementA(ElementA elementA) {
            System.out.println("visitorM visit elementA");
        }

        @Override
        public void visitElementB(ElementB elementB) {
            System.out.println("visitorM visit elementB");
        }
    }

    class VisitorN extends Visitor {

        @Override
        public void visitElementA(ElementA elementA) {
            System.out.println("VisitorN visit elementA");
        }

        @Override
        public void visitElementB(ElementB elementB) {
            System.out.println("VisitorN visit elementB");
        }
    }

    class ElementA extends Element {

        @Override
        public void setVisitor(Visitor visitor) {
            visitor.visitElementA(this);
        }
    }

    class ElementB extends Element {

        @Override
        public void setVisitor(Visitor visitor) {
            visitor.visitElementB(this);
        }
    }

    class ObjectStructure {
        List<Element> list = new ArrayList<>();

        public void add(Element element) {
            list.add(element);
        }

        public void remove(Element element) {
            list.remove(element);
        }

        public void bind(Visitor visitor) {
            for (Element element : list) {
                element.setVisitor(visitor);
            }
        }
    }

}
