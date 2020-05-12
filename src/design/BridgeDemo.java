package design;

/**
 * 桥梁模式
 * 目的：分离抽象和实现
 * 场景：当需要多角度（二维）去分类实现对象，而只用继承会造成大量（两个维度的乘积）的类增加，违背开闭原则，应该考虑用桥接模式
 * 本质：在上述场景中，使一个维度的抽象拥有另一个维度的抽象作为成员变量，避免大量的类继承
 * 常见例子：JDBC驱动
 * 好文：https://www.cnblogs.com/betterboyz/p/9361784.html
 */
public class BridgeDemo extends BaseDemo{
    @Override
    public void run() {
        Driver mysqlDriver= new MysqlDriver();
        JDBC androidJDBC= new AndroidJDBC(mysqlDriver);
        androidJDBC.insertMag("google");

        Driver oracleDriver = new OracleDriver();
        JDBC iosJDBC = new IosJDBC(oracleDriver);
        iosJDBC.insertMag("apple");
    }
    interface Driver{
        void insertMsg(String msg);
    }
    static class MysqlDriver implements Driver{

        @Override
        public void insertMsg(String msg) {
            System.out.println("MysqlDriver insert "+msg);
        }
    }
    static class OracleDriver implements Driver{

        @Override
        public void insertMsg(String msg) {
            System.out.println("OracleDriver insert "+msg);
        }
    }
    abstract static class JDBC{
        Driver driver;
        public JDBC(Driver driver){
            this.driver=driver;
        }
        public  void insertMag(String msg){
            driver.insertMsg(msg);
        }
    }
    static class AndroidJDBC extends JDBC{
        public AndroidJDBC(Driver driver) {
            super(driver);
        }

        @Override
        public void insertMag(String msg) {
            msg="Android msg:"+msg;
            super.insertMag(msg);
        }
        public void androidExtendMethod(){
            System.out.println("Android extend Method");
        }
    }
    static class IosJDBC extends JDBC{
        public IosJDBC(Driver driver) {
            super(driver);
        }

        @Override
        public void insertMag(String msg) {
            msg="Ios msg:"+msg;
            super.insertMag(msg);
        }
        public void iosExtendMethod(){
            System.out.println("Ios extend Method");
        }
    }
}
