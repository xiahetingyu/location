package com.xiahe.processor;

import com.alibaba.fastjson.JSONObject;
import com.xiahe.config.Pool;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class Processor {

    //Take
    public static String take(JSONObject jsonObject) throws Exception {
        Method take = Processor.class.getMethod(jsonObject.getString("type"), JSONObject.class);
        return take.invoke(null, jsonObject).toString();
    }

    //Proxy
    public static String proxy(JSONObject jsonObject) throws Exception {
        JSONObject result = new JSONObject();
        result.put("data", Pool.Pool.poll(3, TimeUnit.SECONDS));
        result.put("response", "success");
//        result.put("type", jsonObject.getString("type"));
        return result.toString();
    }

}