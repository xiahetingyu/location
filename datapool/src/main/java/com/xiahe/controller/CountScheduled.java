package com.xiahe.controller;

import com.xiahe.entity.IdentityGov;
import com.xiahe.repository.IdentityGovRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @description
 * @author: Yue
 * @create: 2020.01.21 21:37
 **/
@Component
public class CountScheduled {

    @Autowired
    private IdentityGovRepository identityGovRepository;

    private long last, count = -1;

    @Scheduled(fixedDelay = 5000000)
    public void select() {
        IdentityGov identityGov = new IdentityGov();
        identityGov.setGov(0);
        Example<IdentityGov> example = Example.of(identityGov);

        if (count == -1) {
            count = identityGovRepository.count();
        }

        //本次查出的
        long size = count - identityGovRepository.count(example);


        //每分钟平均
        System.out.println("每分平均：" + (size - last) * 12);

        System.out.println("当前进度：" + (last = size) / (double) count * 100 + "%");
    }

}
