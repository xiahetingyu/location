package com.xiahe.core;

import com.xiahe.config.Pool;

//收集器核心
public class Core implements Runnable {

    // 收集开始
    private void collect() {
        Pool.Executors.execute(new CollectForCore());
    }

    // 验证开始
    private void verification() {
        // 并发验证器
        for (int i = 0; i < 200; i++) {
            Pool.Executors.execute(new Verification());
        }
    }

    @Override
    public void run() {
        collect();
        verification();
    }

}
