package com.xiahe.controller;

import com.xiahe.pool.Pool;
import com.xiahe.pool.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @description 中国政府网控制器
 * @author: Yue
 * @create: 2020.01.04 23:55
 **/
@RequestMapping("thread")
@RestController
public class IdentityGovController {

    public static List<Version> versions = Collections.synchronizedList(new LinkedList<>());

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("get")
    public String get() {
        return String.valueOf(versions.size());
    }

    @RequestMapping("add")
    public String add(int num) {
        for (int i = 0; i < num; i++) {
            Version version = applicationContext.getBean(Version.class);
            versions.add(version);
            Pool.EXECUTOR.execute(version);
        }
        return get();
    }

    @RequestMapping("end")
    public String end(int num) {
        num = num > versions.size() ? versions.size() : num;
        for (int i = 0; i < num; i++) {
            versions.get(i).end();
        }
        return get();
    }

    @RequestMapping("show")
    public String show() {
        Version.show = !Version.show;
        return "日志详情" + (Version.show ? "已开启" : "已关闭");
    }

}
