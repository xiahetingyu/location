package com.xiahe.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProxySchedule {

    @Scheduled(fixedDelay = 60 * 1000)
    public void refreshProxy() {
        System.out.println("正在刷新代理内容");
    }

}
