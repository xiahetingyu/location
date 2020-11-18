package com.xiahe.tools;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @description 非对称加密工具类
 * @author: Yue
 * @create: 2019.12.29 21:29
 **/
public class RsaTools {
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
        return Base64.encodeBase64String(doFinal);
    }

}
