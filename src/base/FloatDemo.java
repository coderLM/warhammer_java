package base;

import sun.misc.Contended;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class FloatDemo {
    static int min;

    public static void main(String[] args) {
//        System.out.println(min);
        testArray();
    }

    private static void testArray() {
        int[] chrome = new int[]{39, 166, 41, 160, 0, 177, 30, 201, 128, 240, 59, 200, 133, 42, 24, 101, 35, 133, 43, 165, 42, 9, 127, 48, 2, 169, 0, 101, 39, 208, 33, 165, 43, 157, 3, 2, 177, 30, 200, 24, 101, 36, 157, 0, 2, 177, 30, 200, 157, 1, 2, 177, 30, 200, 157, 2, 2, 232, 232, 232, 232, 76, 233, 129, 200, 200, 200, 76, 233, 129, 134, 41, 96, 72, 138, 72, 152, 72, 165, 24, 41, 24, 208, 3, 76, 87, 131, 169, 2, 141, 20, 64, 165, 7, 208, 3, 76, 49, 131, 162, 140, 117, 96, 76, 58, 40, 23, 8, 249, 235, 221, 209, 197, 186, 175, 165, 156, 147, 139, 131, 124, 117, 110, 104, 98, 92, 87, 82, 77, 73, 69, 65, 61, 58, 54, 51, 48, 45, 43, 0, 173, 77, 242, 157, 76, 0, 184, 116, 52, 247, 190, 136, 86, 38, 248, 206, 165, 127, 91, 57, 25, 251, 222, 195, 170, 146, 123, 102, 82, 63, 45, 28, 12, 253, 238, 225, 212, 200, 189, 178, 168, 159, 150, 141, 133, 126, 118, 112, 105, 99, 94, 88, 83, 79, 74, 70, 66, 62, 58, 55};
        int[] android = new int[]{39, 166, 41, 160, 0, 177, 30, 201, 128, 240, 59, 200, 133, 42, 24, 101, 35, 133, 43, 165, 42, 9, 127, 48, 2, 169, 0, 101, 39, 208, 33, 165, 43, 157, 3, 2, 177, 30, 200, 24, 101, 36, 157, 0, 2, 177, 30, 200, 157, 1, 2, 177, 30, 200, 157, 2, 2, 232, 232, 232, 232, 76, 233, 129, 200, 200, 200, 76, 233, 129, 134, 41, 96, 72, 138, 72, 152, 72, 165, 24, 41, 24, 208, 3, 76, 87, 131, 169, 2, 141, 20, 64, 165, 7, 208, 3, 76, 49, 131, 162, 140, 117, 96, 76, 58, 40, 23, 8, 249, 235, 221, 209, 197, 186, 175, 165, 156, 147, 139, 131, 124, 117, 110, 104, 98, 92, 87, 82, 77, 73, 69, 65, 61, 58, 54, 51, 48, 45, 43, 0, 173, 77, 242, 157, 76, 0, 184, 116, 52, 247, 190, 136, 86, 38, 248, 206, 165, 127, 91, 57, 25, 251, 222, 195, 170, 146, 123, 102, 82, 63, 45, 28, 12, 253, 238, 225, 212, 200, 189, 178, 168, 159, 150, 141, 133, 126, 118, 112, 105, 99, 94, 88, 83, 79, 74, 70, 66, 62, 58, 55};
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < android.length; i++) {
            if (android[i] != chrome[i]) {
                strings.add(android[i] + "---" + chrome[i]);
            }
        }
        for (String string : strings) {
            System.out.println(string);
        }
        System.out.println("strings len:" + strings.size());
    }

    //大数吃小数
    private static void floatAddFloat() {
        float a = 20000000.0f;
        float b = 1.0f;
        float c = a + b;
        System.out.println("c is " + c);
        float d = c - a;
        System.out.println("d is " + d);
        int[] array = new int[]{1, 2, 3};
        int[] arrayB = {1, 2};
        int[] arrayC = {3, 4};
        int[][] arrayE = {arrayB, arrayC};
        System.out.println(array.length);
        System.out.println(arrayB.length);
        System.out.println(arrayC.length);
        System.out.println(arrayE[0][0]);
        System.out.println(arrayE[1][0]);
    }

    private static void printFloatByte() {
        System.out.println(Integer.toBinaryString(Float.floatToIntBits(-9.1f)));
//        01000001000100011001100110011001  //9.1 （课件）
        // 1000001000100011001100110011010  //9.1
        //11000001000100011001100110011010  //-9.1
        Stack<Integer> stack = new Stack<>();
    }
}
