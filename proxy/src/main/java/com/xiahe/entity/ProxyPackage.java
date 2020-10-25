package com.xiahe.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.sql.Timestamp;

/**
 * @description 代理实体类
 * @author: Yue
 * @create: 2020.01.01 22:58
 **/
@Getter
@Setter
@ToString
@Entity
@Table(name = "proxy_package")
public class ProxyPackage {

    /**
     * 物理标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Transient
    private Proxy proxy;

    public Proxy getProxy() {
        //在第一次取用时创建
        if (proxy == null) {
            synchronized (this) {
                if (proxy == null) {
                    //创建代理对象
                    proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
                }
            }
        }
        return proxy;
    }

    /**
     * 主机
     */
    private String host;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 最后更新时间
     */
    private Timestamp time;

}
