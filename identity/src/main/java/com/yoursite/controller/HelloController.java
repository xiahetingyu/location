package com.yoursite.controller;

import com.yoursite.client.AmapClient;
import com.yoursite.client.GovClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @description
 * @author: Yue
 * @create: 2020.11.16 21:36
 **/
@RestController
public class HelloController {

    @Autowired
    private AmapClient amapClient;

    @RequestMapping("b")
    public Object hello() {
        Map result = amapClient.getLocation("121.475078", "31.223577");
        System.out.println(result);
        return result;
    }

    @RequestMapping("a")
    public Object aa() {
        String a = "aaaa";
        return a;
    }

    @Autowired
    private GovClient govClient;

    @RequestMapping("regidcard")
    public Object regidcard() {
        String regidcard = govClient.regidcard();
        System.out.println(regidcard);
        return regidcard;
    }

}
