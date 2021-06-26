package base;

import jdk.nashorn.internal.parser.JSONParser;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 字节操作demo
 * 刚好工作中用到蓝牙通信加密
 */
public class BytesDemo {
    public static void main(String[] args) {
        testNumber();
    }

    //2021-01-11 18:53:46.091 System.out: type of value:::4278190080
//2021-01-11 18:53:46.094 System.out: int value:::4283585106
//2021-01-11 18:53:46.097 System.out: arr[0]-11382190
    private static void testNumber() {

        int a = 0xff000000;
        long b = 0xff000000;
        System.out.println("a:" + a);
        System.out.println("b:" + b);
        Long Lb = 0xff000000L;
        System.out.println("b:" + Lb);
        Map<Object, Object> map = new HashMap<>();
        map.put("0", 4278190080L);
        int[] arr = new int[map.size()];
        Long[] array = (Long[]) map.values().toArray(new Long[0]);
        System.out.println("int value:::" + array[0].intValue());
        for (int i = 0; i < array.length; i++) {
            arr[i] = array[i].intValue();
        }
        System.out.println("arr[0]" + arr[0]);
        System.out.println("4283585106-4278190080=" + (4283585106L - 4278190080L));

    }

    private static void testString() {
        String str = "您好";
        Charset encodeState = StandardCharsets.ISO_8859_1;
        byte[] bytes = str.getBytes();
        try {

            byte[] bytes1 = str.getBytes(encodeState);
            for (byte b : bytes1) {
                System.out.println("b:" + b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (byte b : bytes) {
//            System.out.println("b:"+b);
        }
    }

}
