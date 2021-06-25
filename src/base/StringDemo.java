package base;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class StringDemo {
    public static void main(String[] args) throws ClassNotFoundException {
        String a = "abc";
        String b = new String("abc");
        String c = "abc";
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(c.hashCode());
        System.out.println(a == b);
        System.out.println(a.equals(b));
        System.out.println(a == c);

        StringBuilder sb = new StringBuilder();
        sb.append("a");

        //原对象
        Object instance = Class.forName("");
        //类加载器
        ClassLoader classLoader = null;
        //接口
        Class<?>[] interfaces = instance.getClass().getInterfaces();
        Object object = Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //before
                Object result = method.invoke(instance, args);
                //after
                return result;
            }
        });
        //使用
        //object.***()

    }

    private void testM() {
        new InnerA();
    }

    protected class InnerA {
        String a = "abc";
    }
}
