package com.xiahe.service;

import com.xiahe.entity.Identity;
import com.xiahe.repository.IdentityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description 中国政务网服务实现
 * @author: Yue
 * @create: 2020.01.06 01:15
 **/
@Service
public class IdentityServiceImp implements IdentityService {

    private IdentityRepository identityRepository;

    @Autowired
    public IdentityServiceImp(IdentityRepository identityRepository) {
        this.identityRepository = identityRepository;
        end.setId(-1);
    }

    @Override
    public Identity select() {
        return getIdentity();
    }

    @Override
    public void update(Identity identity) {
        identityRepository.saveAndFlush(identity);
    }


    /**
     * 计数器 标记当前位置
     */
    private AtomicInteger index = new AtomicInteger(-1);

    /**
     * 结束标志
     */
    private Identity end = new Identity();

    private BlockingQueue<Identity> identityGovBlockingQueue = new LinkedBlockingQueue<>();

    private boolean flag = true;

    private Identity getIdentity() {
        //如果已经没有数据了就直接返回约定的内容
        if (flag) {
            //如果栈里没有数据了就取
            if (identityGovBlockingQueue.isEmpty()) {
                synchronized (this) {
                    if (identityGovBlockingQueue.isEmpty()) {
                        Page<Identity> all = identityRepository.findAll(PageRequest.of(index.incrementAndGet(), 2048));
                        System.out.println("当前Index：" + index);
                        List<Identity> content = all.getContent();
                        flag = !content.isEmpty();
                        identityGovBlockingQueue.addAll(content);
                    }
                }
            }
            //如果取到了弹出
            if (!identityGovBlockingQueue.isEmpty()) {
                return identityGovBlockingQueue.poll();
            }
        }

        //约定的结束内容
        return end;
    }

    /**
     * 记录已经给出但却没有回来的数据
     */
    private static Map<String, Long> cache = new ConcurrentHashMap<>();
}
