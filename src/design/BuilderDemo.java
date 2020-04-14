package design;

/**
 * 建造者模式
 * 目的：方便创建复杂对象。
 * 实现方式：
 * 第一种：
 * 方式：抽象建造者、具体建造者、指挥者、产品
 * 特点：隐藏内部的建造过程和细节
 * 第二种：
 * 方式：静态内部类
 * 特点：更灵活（使用者可按需实现零件的无序装配）
 * 例：
 * 注意事项：
 * 1.与工厂模式的相比，建造者模式更加关注零件的装配顺序，一般用来创建复杂的对象。
 * 参考：https://www.jianshu.com/p/47329a94f5dc
 */
public class BuilderDemo extends BaseDemo {
    @Override
    public void run() {
        //第一种方式（见参考链接）

        //第二种方式（推荐）
        Computer computer = new Computer.Builder().putCpu("intel").putKeyboard("apple").build();
        computer.getCpu();
        computer.getKeyboard();
    }


}

class Computer {
    private String cpu;
    private String keyboard;

    private void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getCpu() {
        System.out.println("computer cpu:" + this.cpu);
        return cpu;
    }

    private void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getKeyboard() {
        System.out.println("computer keyboard:" + this.keyboard);
        return keyboard;
    }

    static abstract class IBuilder {
        abstract IBuilder putCpu(String cpu);

        abstract IBuilder putKeyboard(String keyboard);

        abstract Computer build();
    }

    static class Builder extends IBuilder {
        Computer computer;

        public Builder() {
            computer = new Computer();
        }

        public Builder putCpu(String cpu) {
            computer.setCpu(cpu);
            return this;
        }

        public Builder putKeyboard(String keyboard) {
            computer.setKeyboard(keyboard);
            return this;
        }

        public Computer build() {
            return computer;
        }

    }
}
