package com.xiahe.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @description 身份证信息
 * @author: Yue
 * @create: 2020.11.22 16:52
 **/
@ToString
@Getter
@Setter
@Entity
@Table(name = "identity")
public class IdentityEntity {

    /**
     * 表ID顺序
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

}
