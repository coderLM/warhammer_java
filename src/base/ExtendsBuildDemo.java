package base;


/**
 * 继承中的初始化顺序
 */
public class ExtendsBuildDemo extends Parent {

    static Member member = new Member("Child 静态成员");
    private Member member1 = new Member("Child 非静态成员");

    static {
        System.out.println("Child 静态代码块");
    }

    {
        System.out.println("Child 代码块");
    }

    public ExtendsBuildDemo() {
        System.out.println("Child 构造函数");
    }

    public static void main(String[] args) {
        new ExtendsBuildDemo();
        System.out.println("-----------------------");
        new ExtendsBuildDemo();
    }
}
class Member {
//    static {
//        System.out.println("成员 静态代码块");
//    }
//
//    {
//        System.out.println("成员 代码块");
//    }

    Member(String i) {
        System.out.println(i);
    }
}

class Parent {
    static Member member = new Member("Parent 静态成员");
    private Member member1 = new Member("Parent 非静态成员");

    static {
        System.out.println("Parent 静态代码块");
    }

    {
        System.out.println("Parent 代码块");
    }

    Parent() {
        System.out.println("Parent 构造函数");
    }
}
