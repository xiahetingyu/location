package com.xiahe.core;

//收集器接口
public interface Collect extends Runnable {

    // 收集方法
    void collect();

    //下一链
    void next();

}
