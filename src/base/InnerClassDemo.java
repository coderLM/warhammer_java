package base;

/**
 * 内部类的继承
 */
public class InnerClassDemo {
    public static void main(String[] args) {
        new InheritInner(new WithInner());
    }
}

class WithInner {

    class Inner {
    }
}

class InheritInner extends WithInner.Inner {
    //         InheritInner() {} // Won't compile
    InheritInner(WithInner wi) {
        wi.super();
    }

    public static void main(String[] args) {
        WithInner wi = new WithInner();
        InheritInner ii = new InheritInner(wi);
    }
}
