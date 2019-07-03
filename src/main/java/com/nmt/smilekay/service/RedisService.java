package com.nmt.smilekay.service;

import java.util.Set;

public interface RedisService {
    void put(String key, Object value, long seconds);
    Object get(String key);
    boolean delete(Object key);
    void update(String key, Object value);
    Set<String> getLoginCodes();
}
