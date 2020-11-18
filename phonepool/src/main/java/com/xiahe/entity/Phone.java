package com.xiahe.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @description 手机号认证实体
 * @author: Yue
 * @create: 2020.01.27 14:16
 **/
@Component
@Getter
@Setter
@ToString
@Entity
@Table(name = "phone")
public class Phone {

    /**
     * 物理标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 手机号
     */
    private String num;

    /**
     * 是否真实的手机号
     * null 未验证
     * true 真实
     * false 虚拟
     */
    private Boolean truly;

    /**
     * 附带的信息
     */
    private String message;

    /**
     * 最后操作的时间
     */
    private Timestamp time;

}
