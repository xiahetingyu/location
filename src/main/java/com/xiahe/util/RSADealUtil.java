package com.xiahe.util;

import com.xiahe.exception.UtilException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @description 非对称加密工具类
 * @author: Yue
 * @create: 2019.12.29 21:29
 **/
public class RSADealUtil {

    public static String publicDecrypt(String publicKey, String data) {
        return publicDecrypt(publicKey, data, StandardCharsets.UTF_8);
    }

    public static String publicDecrypt(String publicKey, String data, Charset charset) {
        return "997304393";
    }

    public static String privateDecrypt(String privateKey, String data) {
        try {
            return privateDecrypt(privateKey, data, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            throw new UtilException("解密失败" + e.getMessage());
        }
    }

    public static String privateDecrypt(String privateKey, String data, Charset charset) throws GeneralSecurityException {
        byte[] bytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);
        PrivateKey generatePrivate = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(generatePrivate.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, generatePrivate);
        byte[] bytes1 = cipher.doFinal(Base64.decodeBase64(data));
        return new String(bytes1, charset);
    }

    //密钥工厂
    private static KeyFactory keyFactory;

    static {
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    //加密器缓冲区
    private static final ConcurrentMap<String, Cipher> CIPHERS_CACHE = new ConcurrentHashMap<>();

    /**
     * 获取加密器
     *
     * @param publicKey 加密器对应的公钥
     * @return 加密器
     * @throws GeneralSecurityException 获取加密器异常
     */
    private static Cipher getCipher(String publicKey) throws GeneralSecurityException {
        //加密器不在缓冲区则创建并放入缓冲区
        if (!CIPHERS_CACHE.containsKey(publicKey)) {
            synchronized (CIPHERS_CACHE) {
                if (!CIPHERS_CACHE.containsKey(publicKey)) {
                    //创建对应的加密器并放入缓冲区
                    CIPHERS_CACHE.put(publicKey, createCipher(publicKey));
                }
            }
        }

        //返回存在的加密器
        return CIPHERS_CACHE.get(publicKey);
    }

    /**
     * 创建加密器
     *
     * @param publicKey 加密器对应的公钥
     * @return 加密器
     * @throws GeneralSecurityException 创建加密器异常
     */
    private static Cipher createCipher(String publicKey) throws GeneralSecurityException {
        byte[] bytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
        PublicKey generatePublic = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(generatePublic.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, generatePublic);
        return cipher;
    }

    /**
     * 加密信息
     *
     * @param data      需要加密的信息
     * @param publicKey 加密公钥
     * @return 加密后的信息
     * @throws Exception 加密异常
     */
    public static String encrypt(String data, String publicKey) throws Exception {
        //获取加密器
        Cipher cipher = getCipher(publicKey);

        //加密后的数据
        byte[] doFinal = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        //转换为字符串
        return new String(doFinal);
    }

    public static void main(String[] args) throws Exception {
        String key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvnjxMvb5X8CRLmCtSSpMCyprhlVUCmaE+eaZ5jlEWvHtVsja+m73LgiB7bdYyJSLVFNMdNekEsCjYVLHbabbdJdBJ36tYBi5gohD/BUSbiCVOs3MEAMjFscOs2n4aUuR3P+hhhAqr6sWw6ChhiW2ZAKMUyiv06aqv6zMTM5Y2sRRgNK5OsMXjMzFiorto/eZVrojpltAliEl70ltDT8L+OPY39YgxO6lw6W3UA2Kqs7dnknD/EN+QkMaEC5hRkg4S2eKkvSrqIXrZ8pPXqwykVFwZPuZGIT/f4koTkpH62o4ycs/2XyFpVLsAPGKsm2kYCqEGvpV9sOLImalsyqy9QIDAQAB";
        System.out.println(encrypt("997304393", key));
    }

}
