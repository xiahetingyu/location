package com.xiahe.service;

/**
 * @description 身份信息服务
 * @author: Yue
 * @create: 2020.01.01 22:21
 **/
public interface IdentityService {

    void identity(String[] prefix, String start, String end, int sex);

    /**
     * 生成身份信息块
     *
     * @param prefix6 前缀
     * @param year    年份
     */
    void generateIdentityBlock(String prefix6, String year);

}
