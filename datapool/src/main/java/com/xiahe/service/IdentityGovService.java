package com.xiahe.service;

import com.xiahe.entity.IdentityGov;

/**
 * @description 中国政务网服务
 * @author: Yue
 * @create: 2020.01.01 22:21
 **/
public interface IdentityGovService {

    /**
     * 查询一个待处理对象
     *
     * @return 待处理对象
     */
    IdentityGov selectOne();

    /**
     * 更新一个待处理对象
     *
     * @param identityGov
     */
    void updateOne(IdentityGov identityGov);

}
