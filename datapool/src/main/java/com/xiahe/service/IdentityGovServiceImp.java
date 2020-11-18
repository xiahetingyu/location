package com.xiahe.service;

import com.xiahe.entity.IdentityGov;
import com.xiahe.repository.IdentityGovRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description 中国政务网服务实现
 * @author: Yue
 * @create: 2020.01.06 01:15
 **/
@Service
public class IdentityGovServiceImp implements IdentityGovService {

    @Autowired
    private IdentityGovRepository identityGovRepository;

    /**
     * 计数器 标记当前位置
     */
    private AtomicInteger index = new AtomicInteger(-1);

    /**
     * 结束标志
     */
    private IdentityGov end = new IdentityGov();

    private BlockingQueue<IdentityGov> identityGovBlockingQueue = new LinkedBlockingQueue<>();

    public IdentityGovServiceImp() {
        end.setId(-1);
    }


    @Override
    public synchronized IdentityGov selectOne() {
        return getIdentityGov();
    }

    private boolean flag = true;

    private IdentityGov getIdentityGov() {

        //如果已经没有数据了就直接返回约定的内容
        if (flag) {
            //如果栈里没有数据了就取
            if (identityGovBlockingQueue.isEmpty()) {
                Page<IdentityGov> all = identityGovRepository.findAll(PageRequest.of(index.incrementAndGet(), 512));
                System.out.println("当前Index：" + index);
                List<IdentityGov> content = all.getContent();
                flag = !content.isEmpty();
                identityGovBlockingQueue.addAll(content);
            }
            //如果取到了弹出
            if (!identityGovBlockingQueue.isEmpty()) {
                return identityGovBlockingQueue.poll();
            }
        }

        //约定的结束内容
        return end;
    }


    @Override
    public void updateOne(IdentityGov identityGov) {
        identityGovRepository.saveAndFlush(identityGov);
    }

}
