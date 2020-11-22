package com.xiahe.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @description 非对称加密算法控制器
 * @author: Yue
 * @create: 2020.11.22 23:48
 **/
@RestController
public class RSADealController {

    @RequestMapping("/controller/generateKeyPair")
    public JSONObject generateKeyPair() throws NoSuchAlgorithmException {
        //返回结果
        JSONObject result = new JSONObject();

        //生成密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        result.put("public", Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        result.put("private", Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));

        return result;
    }

}
