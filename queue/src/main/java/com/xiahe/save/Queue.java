package com.xiahe.save;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Queue {

    public static final ConcurrentMap<String, Map<String, String>> cache = new ConcurrentHashMap<>();

}
