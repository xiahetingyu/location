package com.xiahe.collector;

import com.alibaba.fastjson.JSONObject;
import com.xiahe.config.Pool;
import com.xiahe.core.CollectImplement;
import com.xiahe.tools.Configuration;
import com.xiahe.tools.NetworkTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Implement_QiYun extends CollectImplement {

    @Override
    public void collect() {
        Map<String, String> map = new HashMap<>(Configuration.Browser);
        map.put("Host", "www.89ip.cn");
        for (int i = 1; i < 200; i++) {
            try {
                String url = "http://www.qydaili.com/free/?action=china&page=" + i;// 收集网址
                if (put(pattern(NetworkTools.networkForString(map, null, url, "GET"))) == 0) return;
            } catch (Exception e) {
                e.printStackTrace();
                super.collect();//休眠5秒
            }
        }
    }

    @Override
    public List<JSONObject> pattern(String content) {
        List<JSONObject> es = new ArrayList<>();// 根据网站的不同使用不同的匹配方式
        Pattern pattern = Pattern
                .compile(">(\\d{1,3}\\.){3}\\d{1,3}</td><tddata-title=\"PORT\">\\d+</td>".replaceAll("\\s", ""));
        Matcher matcher = pattern.matcher(content.replaceAll("\\s", ""));
        while (matcher.find()) {
            String[] strings = matcher.group().split("</td><tddata-title=\"PORT\">".replaceAll("\\s", ""));
            if (strings != null && strings[0] != "") {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("from", Implement_QiYun.class.getSimpleName());
                jsonObject.put("ip", strings[0].replaceAll(">", ""));
                jsonObject.put("port", Integer.valueOf(strings[1].replaceAll("</td>", "")));
                es.add(jsonObject);
            }
        }
        return es;
    }

    @Override
    public void next() {
        Pool.Executors.execute(new Implement_89IP());
        super.next();
    }

}
