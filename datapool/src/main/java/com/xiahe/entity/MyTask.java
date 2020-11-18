package com.xiahe.entity;

import com.xiahe.repository.IdentityTrulyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MyTask implements Runnable {
    private Page<Identity> all;
    private IdentityTrulyRepository identityTrulyRepository;

    @Autowired
    public MyTask(IdentityTrulyRepository identityTrulyRepository) {
        this.identityTrulyRepository = identityTrulyRepository;
    }

    public void setAll(Page<Identity> all) {
        this.all = all;
    }

    @Transactional
    @Override
    public void run() {
        int count = 0;
        for (Identity identity : all) {
            identityTrulyRepository.updateByNum(identity.getNum(), true, identity.getMessage());
            if (count++ % 10000 == 0) {
                System.out.println(Thread.currentThread().getName() + "-update:" + identity.getNum());
            }
        }
        identityTrulyRepository.flush();
    }

}