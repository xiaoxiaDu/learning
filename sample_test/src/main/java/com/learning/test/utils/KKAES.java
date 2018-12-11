package com.learning.test.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 */
public class KKAES {
    private static final Logger logger = Logger.getLogger(KKAES.class);

    private static final String AesKey = String.valueOf("8C14E18D631A4FBB");// AES_KEY
    private static final String AesViKey = String.valueOf("0GB2E5C19B391569");// AES_VI_KEY

//    private static final String AesKey = "8C14E18D631A4FBB";
//    private static final String AesViKey = "0GB2E5C19B391569";

    private static KKAES instance = null;

    private KKAES() {

    }

    public static KKAES getInstance() {
        if (instance == null)
            instance = new KKAES();
        return instance;
    }

    /**
     * 加密String明文输入,经过BASE64编码String密文输出
     *
     * @param text
     * @return
     */

    @SuppressWarnings("static-access")
    public static String encrypt(String text) {
        if (StringUtils.isEmpty(text)) {
            return "";
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] keyBytes = new byte[16];
            byte[] ivBytes = new byte[16];

            byte[] b = AesKey.getBytes("UTF-8");
            byte[] v = AesViKey.getBytes("UTF-8");

            int len = b.length;
            int len2 = v.length;

            if (len > keyBytes.length) len = keyBytes.length;
            if (len2 > ivBytes.length) len2 = ivBytes.length;

            System.arraycopy(b, 0, keyBytes, 0, len);
            System.arraycopy(v, 0, ivBytes, 0, len2);

            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            BASE64 decoder = new BASE64();
            byte[] results = cipher.doFinal(text.getBytes("UTF8"));
            return new String(decoder.encode(results));
        } catch (Exception e) {
            logger.error("KKAES >> encrypt << " + e.getMessage(), e);
            return "";
        }
    }

    /**
     * 解密 以BASE64形式String密文输入,String明文输出
     *
     * @param text
     * @return
     */
    @SuppressWarnings("static-access")
    public static String decrypt(String text) {
        if (StringUtils.isEmpty(text)) {
            return "";
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] keyBytes = new byte[16];
            byte[] ivBytes = new byte[16];
            byte[] b = AesKey.getBytes("UTF-8");
            byte[] v = AesViKey.getBytes("UTF-8");

            int len = b.length;
            int len2 = v.length;
            if (len > keyBytes.length) len = keyBytes.length;
            if (len2 > ivBytes.length) len2 = ivBytes.length;
            System.arraycopy(b, 0, keyBytes, 0, len);
            System.arraycopy(v, 0, ivBytes, 0, len2);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            BASE64 decoder = new BASE64();
            byte[] results = cipher.doFinal(decoder.decode(text));
            return new String(results, "UTF-8");
        } catch (Exception e) {
            logger.error("KKAES2 >> decrypt << " + e.getMessage(), e);
            return "";
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String AesKey = "8C14E18D631A4FBB";
        String AesViKey = "0GB2E5C19B391569";

        String text = "91310000087773080X";
        String strenc = KKAES.encrypt(text);//加密
        System.out.println("AES加密结果:" + strenc);
        System.out.println("AES加密结果:" + strenc.length());
        String strDes = KKAES.decrypt("sO8LAgqtJEMRd8xNiuwLXWKrqCOjQMQwAjqLxDJ+Y2U=");//解密
        System.out.println("AES解密结果:" + strDes);

    }
}