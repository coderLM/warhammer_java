package encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Logger;

public class Rsa {
    private static final String KEYTYPE = "RSA";
    // RSA最大加密明文大小
    private final static int MAX_ENCRYPT_BLOCK = 117;
    // RSA最大解密密文大小
    private final static int MAX_DECRYPT_BLOCK = 128;
    private final static String ALGORITHM_MODE_PADDING = "RSA/ECB/PKCS1Padding";
    private final static String ALGORITHM = "RSA";

    private static final Base64.Decoder decoder = Base64.getDecoder();

    private static final Base64.Encoder encoder = Base64.getEncoder();

    private String rsaPubKey;
    private String rsaPriKey;

    //生成公钥与私钥
    public boolean createkey() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEYTYPE);
            generator.initialize(1024);
            KeyPair keyPair = generator.generateKeyPair();
            //publicKey
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            //privateKey
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            this.rsaPubKey = new String(encoder.encode(publicKey.getEncoded()));
            this.rsaPriKey = new String(encoder.encode(privateKey.getEncoded()));
            return true;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    public String getRsaPubKey() {
        return rsaPubKey;
    }

    public String getRsaPriKey() {
        return rsaPriKey;
    }

    /**
     * RSA使用私钥进行解密
     *
     * @param data 解密数据
     * @param key  私钥
     * @return 解密后的结果
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key) {
        byte[] result = null;
        try {
            byte[] bytes = decoder.decode(key);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.length; i += MAX_DECRYPT_BLOCK) {
                if (i + MAX_DECRYPT_BLOCK <= data.length) {
                    byte[] doFinal = cipher.doFinal(data, i, MAX_DECRYPT_BLOCK);
                    result = ArrayUtils.addAll(result, doFinal);
                } else {
                    byte[] doFinal = cipher.doFinal(data, i, data.length - i);
                    result = ArrayUtils.addAll(result, doFinal);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //使用公钥进行加密
    public static byte[] encryptByPublicKey(byte[] data, String Key) {
        byte[] result = null;
        try {
            byte[] bytes = decoder.decode(Key);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
            PublicKey publicKey = factory.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            for (int i = 0; i < data.length; i += MAX_ENCRYPT_BLOCK) {
                // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + MAX_ENCRYPT_BLOCK));
                result = ArrayUtils.addAll(result, doFinal);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
//        Rsa rsa = new Rsa();
//        rsa.createkey();
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJTKlX6TSNA45K+tiKsnmEDx8ArnSgHnBR579PWXXsR9e/JQ66ursPMdUgQcRKYxqmgF81pKQkvE5xhqydx+NCN7tMOLJlZ9LX0Js23LOPDDWlio697I8DXM+ztpTLM8Pz5kPGgntaNVNQ1UmA5HVfSetbwH+WwtJy/isJ2YX7kwIDAQAB";
        String privacyKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIlMqVfpNI0Djkr62IqyeYQPHwCudKAecFHnv09ZdexH178lDrq6uw8x1SBBxEpjGqaAXzWkpCS8TnGGrJ3H40I3u0w4smVn0tfQmzbcs48MNaWKjr3sjwNcz7O2lMszw/PmQ8aCe1o1U1DVSYDkdV9J61vAf5bC0nL+KwnZhfuTAgMBAAECgYBIjK+2+k2Abe6UhDREJca8F1lkfMq3iR4EyZZ3kWgUcGn6YNSFqwIJJ2Xc46ObQv7kGFU2/UaW+sHgjy9LOEJHBpkRCIQLLY6Ah9J3wwh45+6L7iX1f70uFKc8S6mk8rtXDer3e3jaekbQaH3hovwk/XlfNNkaFuMQmeoDXnEBIQJBAMkajd/xbQKsLoJFRM+Fm/bJLidz29mLHOTeumoB5+m8IWowpNf3KdQfNhR0/web5CIvu3JWvmt+faEua2tYBrkCQQCux11Ft7tlTR28BGzBOlb3nxc8afC+O9j4nntmOdlOflMS+9XB37y9J+bkn0QZkpZ6/6V2rr6lJaBfYJ0+hm6rAkEAgC34uAB95vIwQsA797nFiyr3zBXAz4wnwolkcCT+2LAc7D5NE4pqktXB9pAL7aGja1bZNyuq+4EIOjAIL8dPoQJAPoRNVqy7uSZ2ZlRm2k8V4IjldNSoKkOZ/oQhu5Uhp7QD3xx5f1maV0a4jjpxz3vhm6lp9CPZSZVF4hfa5wyK9QJACs9X5rbCxmlBYPLCkqrM4fWDHnhgoHWfUaCZCvADfrjYzbKdHnWMntEIRkN8LMt8Snqt60XcCQf4kZDfo8K5IQ==";
        String content = "abcdefg";
//        byte[] enResult = Rsa.encryptByPublicKey(content.getBytes(StandardCharsets.UTF_8), publicKey);
//        System.out.println("result:::" + Arrays.toString(enResult));
        String dartResult = "SdPCg85DhAXExVeFyWAvj4FdjvjyY+wmc6hf9EAEfY+eso+gkqcI79Q6HM0VR/ND9MqU27UQm3Op4L99MlVU61TMrg2SoLFvIgj573nuRG4dx4IMGbYnZc/Xm99Z73ky8svqK0hJYcgSjhdb9/cDeGkjmg6p2it03L8lJXnDUKM=";
        byte [] enResult=decoder.decode(dartResult);
        System.out.println("deResult:::" + new String(Rsa.decryptByPrivateKey(enResult, privacyKey)));

    }
//    publicKey len:::216
//    publicKey :::MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCvUfe4a8KDL/eCLehzl0LBgF41vPZ9MId6MlSwBPbnja3C5Y/rLaWpTQleVUGIqSezPaNo6GCdK6tO2WWhSHiQtrafihrM94z6C+xNKmkPEmxJoV0vDGHkcXhgCbR8Ds+N++ajRgDyO9ml2UKPGVckCDT5juQKNfJerag7/XpNCQIDAQAB
//    result:::[119, 107, -43, 95, -57, 87, 35, 83, 7, -126, 108, 67, -12, -71, 104, 112, -33, 50, -45, 6, 39, -118, 49, -9, 102, -89, -108, 127, -95, -46, 79, 112, 122, -63, -122, 71, 83, -118, 1, -14, 8, -82, 114, 38, -12, 52, 25, 50, -66, 88, -36, 35, 38, -126, -113, 96, 93, -55, 0, 91, 86, 76, 68, -7, -1, 32, 87, 84, 45, 116, 77, 108, -10, 74, -68, 38, -19, -123, -48, 117, 25, -24, -99, -55, -94, 63, -15, 81, -28, -72, 22, 93, -74, 122, -121, 101, 24, 45, 52, 63, 2, 33, -12, -121, -95, -112, 50, 119, 116, 73, -2, 16, 102, -83, -64, -55, 54, 110, 127, 117, -12, 112, 0, -52, -41, -7, 24, -85]

}

