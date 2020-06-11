package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

public class ErrorDemo {
    public static void main(String[] args) {
//        System.out.println(new ErrorDemo().test());
        int x=0;
        assert x>0;
    }

    int test() {
        try (Scanner in = new Scanner(new FileInputStream("/Users/sm-li/warhammer_java/src/base/ErrorDemo.java"), "UTF-8");
             PrintWriter out = new PrintWriter("out.txt")) {
            while (in.hasNext()) {
                System.out.println(in.next());
            }
            return 1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            e.getSuppressed();
        } finally {
            return 2;
        }
    }
}
