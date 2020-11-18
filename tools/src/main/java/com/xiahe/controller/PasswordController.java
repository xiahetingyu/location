package com.xiahe.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 密码转换服务
 * @author: Yue
 * @create: 2019.12.30 23:12
 **/
@RequestMapping("password")
@RestController
public class PasswordController {

    /**
     * 转换为SHA1加密的数据
     *
     * @param password 待加密的数据
     * @return 加密后的数据
     */
    @RequestMapping("sha1")
    public String sha1(String password) {
        return DigestUtils.sha1Hex(password).toUpperCase();
    }

}
