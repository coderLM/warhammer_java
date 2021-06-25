package error;

/**
 * try catch finally 的return结果
 * 1.总是会返回可达的最后一个return所对应的结果
 * 2.如果返回的是局部变量，则在1中所指return之后对这个变量的操作无效（原理是局部变量的值已经存入寄存器，这也是程序员愿意看到的结果）
 */
public class ReturnDemo {
    public static void main(String[] args) {
        System.out.println(test1());

        StackOverflowError so;
        OutOfMemoryError oom;
    }

    private static int test0() {
        try {
            System.out.println(1 / 0);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        } finally {
            return 2;//--> print 2
        }
    }

    private static int test1() {
        int a = 0;
        try {
//            System.out.println(1 / 0);
            return a;//--> 从此return 返回的a的值已经存入寄存器
        } catch (Exception e) {
            e.printStackTrace();
            a = 1;
        } finally {
            a = 2;
            System.out.println("a:::" + a);
//            当在finally中 没有返回语句时//--> print 0
//            return 9;//--> print 9
//            return a;//--> print 2
        }
        return 8;
    }
    private static void test2() {
        NullPointerException exception;

    }
}
