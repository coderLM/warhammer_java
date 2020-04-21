package design;

/**
 * 装饰模式
 * 目的：为已有功能，动态添加更多动能（除了继承之外，另一种获取某类特性的方法，更灵活）
 * 实现：
 * 1.装饰类与被装饰类有相同的父类（或实现相同的接口）
 * 2.装饰类持有被装饰类类型的成员变量
 * 3.装饰类在自身方法中调用被装饰类实例的方法，并进行扩展
 * 优点：
 * 有效地把类的核心职责和装饰功能区分开，可以去除相关类中重复的装饰逻辑
 * 场景：
 * 1.不方便写子类（需要的子类爆发式增长）
 * 2.无法写子类
 */
public class DecorateDemo extends BaseDemo{
    @Override
    public void run() {
        Person hammer = new PersonDecorateRich(new PersonDecorateHigh(new Hammer()));
        hammer.show();
    }
}

abstract class Person {
    public abstract void show();
}

class Hammer extends Person {

    @Override
    public void show() {
        System.out.println("i am hammer");
    }
}

class PersonDecorateHigh extends Person {
    Person person;

    public PersonDecorateHigh(Person p) {
        this.person = p;
    }

    @Override
    public void show() {
        if (this.person != null) {
            this.person.show();
        }
        System.out.println("i am 1.8m");
    }
}

class PersonDecorateRich extends Person {
    Person person;

    public PersonDecorateRich(Person p) {
        this.person = p;
    }

    @Override
    public void show() {
        if (this.person != null) {
            this.person.show();
        }
        System.out.println("i am richer");
    }
}

