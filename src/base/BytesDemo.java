package base;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 字节操作demo
 * 刚好工作中用到蓝牙通信加密
 */
public class BytesDemo {
    public static void main(String[] args) {
        String str = "您好";
        Charset encodeState=StandardCharsets.ISO_8859_1;
        byte[] bytes = str.getBytes();
        try {

            byte[] bytes1= str.getBytes(encodeState);
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
