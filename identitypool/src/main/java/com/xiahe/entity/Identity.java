package com.xiahe.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @description 身份认证实体
 * @author: Yue
 * @create: 2020.01.27 14:16
 **/
@Component
@Getter
@Setter
@ToString
@Entity
@Table(name = "identity")
public class Identity {

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
     * 是否真实的身份
     * null 未验证
     * true 真实身份
     * false 虚拟身份
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
