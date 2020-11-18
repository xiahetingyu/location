package com.xiahe.controller;

import com.xiahe.entity.IdentityGov;
import com.xiahe.service.IdentityGovService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 中国政府网控制器
 * @author: Yue
 * @create: 2020.01.01 22:19
 **/
@RequestMapping("identity_gov")
@RestController
public class IdentityGovController {

    @Autowired
    private IdentityGovService identityGovService;

    @RequestMapping("selectOne")
    public IdentityGov selectOne() {
        return identityGovService.selectOne();
    }

    @RequestMapping("updateOne")
    public void updateOne(@RequestBody IdentityGov identityGov) {
        identityGovService.updateOne(identityGov);
    }

}
