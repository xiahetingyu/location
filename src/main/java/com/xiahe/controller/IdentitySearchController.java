package com.xiahe.controller;

import com.xiahe.service.IdentitySearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 身份证号查询控制器
 * @author: Yue
 * @create: 2020.11.22 08:10
 **/
@RestController
public class IdentitySearchController {

    @Autowired
    private IdentitySearchService identitySearchService;

    @RequestMapping("hello")
    public Object hello(String identity) {
        return identitySearchService.search(identity);
    }

}
