package com.xiahe.service;

import com.xiahe.entity.Identity;

/**
 * @description 中国政务网服务
 * @author: Yue
 * @create: 2020.01.01 22:21
 **/
public interface IdentityService {

    Identity select();

    void update(Identity identity);

}
