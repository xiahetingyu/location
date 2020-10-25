package com.xiahe.core;

import com.xiahe.collector.Implement_89IP;
import com.xiahe.config.Pool;

//编码需要固定的类|核心指定调用的收集器
public class CollectForCore extends CollectImplement {

    @Override
    public void run() {
        Pool.Executors.execute(new Implement_89IP());
    }

}
