package algorithm;

import java.text.DecimalFormat;

public class DivideDemo {
    public static void main(String[] args) {
        int x = 1111;
        double result = sqrtFloat1(x);
        System.out.println(result);
    }

    //平方根--保留6位小数
    private static double sqrtFloat1(int x) {
        int intV = sqrt(x);
        if (intV * intV == x) return intV;
        //(i+f)(i+f) = x --> f*f + 2*i*f = x - i*i
        //所以，得到整数部分之后，只需计算小数部分即可
        long endV = x - intV * intV;
        double l = 0;
        double r = 1;
        double m;
        while (l <= r) {
            m = l + (r - l) / 2;
            if (m * m + m * intV * 2 <= endV) {
                l = m + 0.000001;
            } else {
                r = m - 0.000001;
            }
        }
        return intV + Double.parseDouble(((l / 2 + r / 2) + "").substring(0, 8));
    }

    //平方根--保留整数
    private static int sqrt(int x) {
        int l = 0;
        int r = x;
        int m;
        while (l <= r) {
            m = l + (r - l) / 2;
            if ((long) m * m <= x) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return l / 2 + r / 2;
    }
}
