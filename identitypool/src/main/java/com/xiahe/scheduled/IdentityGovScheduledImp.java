package com.xiahe.scheduled;

import com.xiahe.service.IdentityGovService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @description 中国政府网计划
 * @author: Yue
 * @create: 2020.01.01 22:30
 **/
@Component
public class IdentityGovScheduledImp implements IdentityGovScheduled {

    @Autowired
    private IdentityGovService identityGovService;

    @Scheduled(fixedDelay = 5000)
    @Override
    public void scheduled() {

    }

}
