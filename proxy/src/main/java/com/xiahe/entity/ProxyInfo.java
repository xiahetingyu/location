package com.xiahe.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description 代理信息
 * @author: Yue
 * @create: 2020.11.01 09:46
 **/
@ToString
@Getter
@Setter
public class ProxyInfo {

    /**
     * IP地址
     */
    private String ip;

    /**
     * IP端口
     */
    private Integer port;

}
