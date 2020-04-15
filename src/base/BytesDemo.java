package base;

import java.io.UnsupportedEncodingException;

/**
 * 字节操作demo
 * 刚好工作中用到蓝牙通信加密
 */
public class BytesDemo {
    public static void main(String[] args) {
        String str = "你mx";
        byte[] bytes = str.getBytes();
        try {
            byte[] bytes1= str.getBytes("utf-8");
            for(byte b : bytes1){
                System.out.println("b:"+b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(byte b : bytes){
//            System.out.println("b:"+b);
        }
    }
}
