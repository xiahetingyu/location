package com.xiahe.collector;

import com.xiahe.save.Queue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作类
 */
@RestController
public class OperateController {

    @RequestMapping("add")
    public Boolean add(String host, String port) {
        Map<String, String> map = new HashMap<>();
        map.put("host", host);
        map.put("port", port);
        Queue.cache.put(host + port, map);
        return true;
    }

//    public

    //要是可以去重的保存机制  添加和取出  我是不是已经偏离的太远了  感觉当前做的事情和我期望的事情完全不是一个
}
