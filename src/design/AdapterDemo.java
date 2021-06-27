package design;

/**
 * 适配器模式
 * 目的：转换接口，将一个类的接口转换为需要的接口
 * 场景：
 * 1.Android ListView解耦数据与使用
 * 2.复用类
 * 模式分类：
 * 1.类适配模式：通过继承
 * 2.接口适配模式：
 * 3.对象适配模式：常用
 * PS：谨慎使用
 */
public class AdapterDemo extends BaseDemo{
    @Override
    public void run() {
        //对象适配模式
        Adapter adapter=new Adapter(new Mac());
        new DellScreen().show(adapter.outPut());
    }
    class Mac{
        public String outPut(){
            return "数字 妹子";
        }
    }
    class Adapter{
        public Adapter(Mac mac){
            this.mac=mac;
        }
        Mac mac;
        public String outPut(){
           return mac.outPut().replace("数字","模拟");
        }
    }
    class DellScreen{
        public void show(String info){
            System.out.println("显示图像:"+info);
        }
    }
}
