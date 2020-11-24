package com.xiahe.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @description 配置信息
 * @author: Yue
 * @create: 2020.11.22 16:56
 **/
@ToString
@Getter
@Setter
@Entity
@Table(name = "t_config")
public class ConfigEntity {

    /**
     * 表ID顺序
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 配置的键
     */
    private String myKey;

    /**
     * 配置的值
     */
    private String myValue;

}
