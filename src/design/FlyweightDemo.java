package design;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元模式
 * 目的：对象复用（运用共享技术支持大量细粒度对象的复用）
 * 方法：单例工厂按需创建实例
 * 注意：内蕴状态可以共享，外蕴状态随环境的改变而改变
 * 常见例子：Java的String
 * 优点：
 * 1.大幅度地降低内存中对象的数量
 * 缺点：
 * 1.享元模式使得系统更加复杂。为了使对象可以共享，需要将一些状态外部化，这使得程序的逻辑复杂化。
 * 2.享元模式将享元对象的状态外部化，而读取外部状态使得运行时间稍微变长。
 * 参考：
 * https://www.cnblogs.com/java-my-life/archive/2012/04/26/2468499.html
 */
public class FlyweightDemo extends BaseDemo {
    @Override
    public void run() {
        WebsiteFactory factory = WebsiteFactory.getInstance();
        factory.create("IT").operation("Beijing");
        factory.create("IT").operation("Shanghai");
        factory.create("Story").operation("Beijing");
        factory.printCount();
    }
}

abstract class Website {
    String name;//内部状态

    public Website(String name) {
        this.name = name;
    }

    public abstract void operation(String location);//外部状态
}

class BlogWebsite extends Website {

    public BlogWebsite(String name) {
        super(name);
    }

    @Override
    public void operation(String location) {
        System.out.println(this.name + " BlogWebsite running location:" + location);
    }
}

class WebsiteFactory {
    private static Map<String, Website> map = new HashMap<>();

    private WebsiteFactory() {

    }

    private static final WebsiteFactory instance = new WebsiteFactory();

    public static WebsiteFactory getInstance() {
        return instance;
    }

    public Website create(String name) {
        if (map.get(name) == null) {
            map.put(name, new BlogWebsite(name));
        }
        return map.get(name);
    }

    public void printCount() {
        System.out.println("instance count is:" + map.keySet().size());
    }

}