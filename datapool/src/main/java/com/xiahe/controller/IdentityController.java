package com.xiahe.controller;

import com.xiahe.entity.Identity;
import com.xiahe.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 中国政府网控制器
 * @author: Yue
 * @create: 2020.01.01 22:19
 **/
@RequestMapping("identity")
@RestController
public class IdentityController {

    private IdentityService identityService;

    @Autowired
    public IdentityController(IdentityService identityService) {
        this.identityService = identityService;
    }

    /**
     * 获取一条未验证过的数据
     *
     * @return 待验证数据
     */
    @RequestMapping("select")
    public Identity select() {
        return identityService.select();
    }

    /**
     * 更新一条验证过的数据
     *
     * @param identity 待更新数据
     */
    @RequestMapping("update")
    public void update(@RequestBody Identity identity) {
        identityService.update(identity);
    }


    /**
     * 用于记录当前进度
     */
    private static Map<String, Object> progress = new HashMap<>();

    /**
     * 定时更新进度
     */
    @Scheduled(fixedDelay = 5000)
    public void count() {
        progress.put("key", System.currentTimeMillis());
    }

    /**
     * 查询当前进度
     *
     * @return 当前进度
     */
    @RequestMapping("progress")
    public Map<String, Object> progress() {
        return progress;
    }

}
