package com.xiahe.controller;

import com.xiahe.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 信息生成服务
 * @author: Yue
 * @create: 2019.12.30 23:12
 **/
@RequestMapping("complex")
@RestController
public class ComplexController {

    private IdentityService identityService;

    @Autowired
    public ComplexController(IdentityService identityService) {
        this.identityService = identityService;
    }

    //生成身份证号
    @RequestMapping("identity")
    public void identity() {

    }

}
