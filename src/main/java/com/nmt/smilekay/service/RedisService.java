package com.nmt.smilekay.service;

public interface RedisService {
    void put(String key, Object value, long seconds);
    Object get(String key);
}
