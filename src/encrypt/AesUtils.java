package encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class AesUtils {

    private final static String AesKeyType = "AES";
    private final static String AesCipherType = "AES/CBC/NoPadding";//"AES/ECB/PKCS5Padding";
    private static byte[] ivkey;

    public static void main(String[] args) {
        testBase64();
    }

    private static void testBase64() {
        byte[] arr = new byte[]{1, 1, 1, 2};//AQEBAg==
        byte[] baseB4 = Base64.getEncoder().encode(arr);
        printArray(arr);
        printArray(baseB4);
        printArray(Base64.getDecoder().decode(baseB4));
        System.out.println(Base64.getEncoder().encodeToString(arr));
    }

    private static void testEn() {
        String data = "100,200,300";
        String key = "kGnsH+DTj/Kbtiqeu7HFaKagTHr5y4Q1";
        ivkey = key.getBytes();
//        byte[] b = encrypthPKCS7Padding(data.getBytes(), key);
        byte[] b = new byte[]{};
        try {
            b = encrypt(data, key);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        System.out.println("b len:::" + b.length);
        printArray(b);
        String enstr = new String(Base64.getEncoder().encode(b));
        System.out.println(enstr);
        System.out.println(decryptPkCS7Padding(Base64.getDecoder().decode("hZ3+ABwTMteGzpoZU3pYgbrcMlhjQ9JFw97eBmG1c7MC6AzC98BH7ybgEyMXl7SVRv4H40iEPt+tRbmmiMS58Q=="), key));
        //[239, 45, 124, 6, 199, 215, 95, 146, 104, 162, 212, 52, 122, 87, 55, 150]
        //-17 45 124 6 -57 -41 95 -110 104 -94 -44 52 122 87 55 -106
        //7y18BsfXX5JootQ0elc3lg==
    }

    private static void printArray(byte[] arr) {
        for (byte b : arr) {
            System.out.print(b + " ");
        }
        System.out.println("");
    }

    public static String decryptPkCS7Padding(byte[] data, String secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            Key key = keyByteToKey(secretKey.getBytes());
            IvParameterSpec iv = new IvParameterSpec(ivkey, 0, 16);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] original = cipher.doFinal(data);
            byte[] bytes = decode(original);
            return new String(bytes);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] encrypthPKCS7Padding(byte[] data, String secretKey) {
        try {
            byte[] padBytes = encode(data.length);
            byte[] unencrypted = new byte[padBytes.length + data.length];
            System.arraycopy(data, 0, unencrypted, 0, data.length);
            System.arraycopy(padBytes, 0, unencrypted, data.length, padBytes.length);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            Key key = keyByteToKey(secretKey.getBytes());
            IvParameterSpec iv = new IvParameterSpec(ivkey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encrypted = cipher.doFinal(unencrypted);
            return encrypted;
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] encrypt(String data, String secret)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes("utf-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(ivkey, 0, 16);
        String ivString = Base64.getEncoder().encodeToString(iv.getIV());
        System.out.println("ivString:::" + ivString);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
        byte[] ret = cipher.doFinal(data.getBytes("utf-8"));
        return ret;
    }

    public static byte[] decode(byte[] data) {
        int pad = (int) data[data.length - 1];
        if (pad < 1 || pad > 32) {
            pad = 0;
        }
        return Arrays.copyOfRange(data, 0, data.length - pad);
    }

    private static final int BLOCK_SIZE = 32;
    static Charset CHARSET = Charset.forName("utf-8");

    public static byte[] encode(int count) {
        int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
        if (amountToPad == 0) {
            amountToPad = BLOCK_SIZE;
        }
        char padChr = (char) (amountToPad & 0xFF);
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < amountToPad; index++) {
            sb.append(padChr);
        }
        return sb.toString().getBytes(CHARSET);
    }

    //字节类型的密钥转换成Key对象
    private static Key keyByteToKey(byte[] bytes) {
        SecretKeySpec spec = null;
        try {
            spec = new SecretKeySpec(bytes, AesKeyType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spec;
    }
}