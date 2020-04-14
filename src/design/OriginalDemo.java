package design;

import java.io.*;

/**
 * 原形模式
 * 目的：快速获取对象
 * 涉及知识点：
 * 浅拷贝：引用类型，只能拷贝引用
 * 深拷贝：引用类型，可以拷贝对象，真正的clone；
 *  实现方式：
 *      第一种：implements Cloneable(标记接口，无方法)、重写clone方法-->每级如此
 *      第二种：序列化、返序列化，使用Serializable(标记接口，无方法)
 */
public class OriginalDemo implements Serializable  {
    public static void main(String[] args) {
        OriginalDemo demo = new OriginalDemo();
        demo.run();
    }

    public void run() {
        //原始对象
        Device device = new Device();
        device.setFirmInfo(new FirmInfo().setVersion("1.0.0"));
        device.setId("01");

        try {
            //浅拷贝
            Device cloneDevice = (Device) device.clone();
            cloneDevice.setId("02");
            cloneDevice.getFirmInfo().setVersion("2.0.0");

            //深拷贝
            Device deppCloneDevice = (Device) device.deepClone();
            deppCloneDevice.setId("03");
            deppCloneDevice.getFirmInfo().setVersion("3.0.0");
            //输出
            System.out.println("device id:" + device.getId());
            System.out.println("device firmInfo:" + device.getFirmInfo().getVersion());
            System.out.println("cloneDevice id:" + cloneDevice.getId());
            System.out.println("cloneDevice firmInfo:" + cloneDevice.getFirmInfo().getVersion());
            System.out.println("deppCloneDevice id:" + deppCloneDevice.getId());
            System.out.println("deppCloneDevice firmInfo:" + deppCloneDevice.getFirmInfo().getVersion());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class Device implements Serializable, Cloneable {
        String id;
        FirmInfo firmInfo;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setFirmInfo(FirmInfo firmInfo) {
            this.firmInfo = firmInfo;
        }

        public FirmInfo getFirmInfo() {
            return firmInfo;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        //深度拷贝
        public Object deepClone() throws Exception {
            // 序列化
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(this);

            // 反序列化
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);

            return ois.readObject();
        }
    }

    class FirmInfo implements Serializable {
        String version;

        public FirmInfo setVersion(String version) {
            this.version = version;
            return this;
        }

        public String getVersion() {
            return version;
        }
    }

}
