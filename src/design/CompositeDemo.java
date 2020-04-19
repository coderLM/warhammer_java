package design;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式
 * 目的：构建层次结构，用户可以忽略组合对象与单个对象的不同，统一使用所有对象
 */
public class CompositeDemo extends BaseDemo {
    @Override
    public void run() {
        Component root = new Composite("root");
        root.add(new Leaf("LeafA"));
        root.add(new Leaf("LeafB"));
        Composite leafC = new Composite("LeafC");
        leafC.add(new Leaf("LeafCA"));
        leafC.add(new Leaf("LeafCB"));
        Leaf leafCC = new Leaf("LeafCC");
        leafC.add(leafCC);
        root.add(leafC);
        root.display(0);
        leafC.remove(leafCC);
        root.display(0);
    }

    abstract class Component {
        String name;

        public Component(String name) {
            this.name = name;
        }

        public abstract void add(Component component);

        public abstract void remove(Component component);

        public void display(int depth) {
            System.out.println(name + "," + depth);
        }
    }

    class Composite extends Component {
        List<Component> children = new ArrayList<>();

        public Composite(String name) {
            super(name);
        }

        @Override
        public void add(Component component) {
            children.add(component);
        }

        @Override
        public void remove(Component component) {
            children.remove(component);
        }

        @Override
        public void display(int depth) {
            super.display(depth);
            for (Component component : children) {
                component.display(depth + 1);
            }
        }
    }

    class Leaf extends Component {

        public Leaf(String name) {
            super(name);
        }

        @Override
        public void add(Component component) {
            System.out.println("can't add");
        }

        @Override
        public void remove(Component component) {
            System.out.println("can't remove");
        }
    }

}
