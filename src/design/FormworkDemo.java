package design;

/**
 * 模板模式
 * 目的：提取公共方法，去除子类重复代码
 * 写法：父类定义base方法（用final修饰）、模板方法、钩子方法-->子类重写模板方法、钩子方法
 * 涉及知识点：
 * 钩子方法：子类重写钩子方法，对父类的运行产生影响
 */
public class FormworkDemo extends BaseDemo {
    @Override
    public void run() {
        Person lili =new LiLi();
        Person yuyu=new YuYu();
        lili.life();
        yuyu.life();
    }

    abstract class Person {
        abstract void eat();
        abstract void speak();
        abstract void wear();
        abstract boolean isNight();//钩子方法
        public final void life() {//base方法
            eat();
            speak();
            if(isNight()){
                wear();
            }
        }
    }
    class LiLi extends Person{

        @Override
        void eat() {
            System.out.println("lili eat meat");
        }

        @Override
        void speak() {
            System.out.println("lili speak chinese");
        }

        @Override
        void wear() {
            System.out.println("lili wear nothing");
        }

        @Override
        boolean isNight() {
            return true;
        }
    }
    class YuYu extends Person{

        @Override
        void eat() {
            System.out.println("yuyu eat pizza");
        }

        @Override
        void speak() {
            System.out.println("yuyu speak english");
        }

        @Override
        void wear() {
            System.out.println("yuyu wear underwear");
        }

        @Override
        boolean isNight() {
            return false;
        }
    }
}
