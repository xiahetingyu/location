package com.xiahe.controller;

import com.xiahe.entity.Identity;
import com.xiahe.entity.MyTask;
import com.xiahe.repository.IdentityRepository;
import com.xiahe.repository.IdentityTrulyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 * @author: Yue
 * @create: 2020.02.15 20:59
 **/
@RestController
@RequestMapping("a")
@Transactional
public class UpdateController {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    IdentityRepository identityRepository;

    @Autowired
    IdentityTrulyRepository identityTrulyRepository;

    private boolean temp;

    @RequestMapping("a")
    public synchronized String update() throws InterruptedException {
        if (temp) {
            return "false";
        }

        Runnable runnable = () -> {
            System.out.println("update start");
            int page = 0;
            while (true) {
                Page<Identity> all = identityRepository.findAll(PageRequest.of(page++, 50000));
                if (all.isEmpty()) {
                    break;
                }
                MyTask myTask = applicationContext.getBean(MyTask.class);
                myTask.setAll(all);
                myTask.run();
            }
            System.out.println("update stop");
        };

        new Thread(runnable).start();

        temp = true;
        return "true";
    }

}


