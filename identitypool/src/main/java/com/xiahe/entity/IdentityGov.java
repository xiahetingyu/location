package com.xiahe.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @description 中国政府网身份认证实体类
 * @author: Yue
 * @create: 2020.01.01 21:51
 **/
@Getter
@Setter
@ToString
@Entity
@Table(name = "identity_gov")
public class IdentityGov {

    /**
     * 物理标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 身份证
     */
    private String num;

    /**
     * 姓名
     */
    private String name;

    /**
     * 是否注册该网站
     * 0未进行过验证
     * 1已注册该网站
     * 2未注册该网站
     */
    private Integer registered;

    /**
     * 注册时的附加信息
     */
    private String registeredMessage;

    /**
     * 验证身份信息是否符合
     * 0未进行验证
     * 1信息符合
     * 2信息不符合
     */
    private Integer gov;

    /**
     * 验证时附带的信息
     */
    private String govMessage;

    /**
     * 最后操作的时间
     */
    private Timestamp time;

}
