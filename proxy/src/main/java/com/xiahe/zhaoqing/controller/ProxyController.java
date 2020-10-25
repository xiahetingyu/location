package com.xiahe.zhaoqing.controller;

import com.alibaba.fastjson.JSONObject;
import com.xiahe.processor.Processor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("proxypool")
@RestController
public class ProxyController {

    @RequestMapping("hello")
    public String hello() {
        return "我这没资源 服务器也很烂 不要再搞我了";
    }

    @RequestMapping("proxy")
    public synchronized Map<String, Object> proxy() throws Exception {
        return JSONObject.parseObject(Processor.proxy(null)).getJSONObject("data");
    }

}
