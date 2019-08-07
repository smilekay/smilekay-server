package com.nmt.smilekay.service.impl;

import com.nmt.smilekay.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void put(String key, Object value, long seconds) {
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean delete(Object key) {
        return redisTemplate.delete(key);
    }

    @Override
    public void update(String key, Object value) {
        redisTemplate.opsForValue().getAndSet(key, value);
    }

    @Override
    public Set<String> getLoginCodes() {
        Set<String> loginCodes = new HashSet<>();
        Set<String> keys = redisTemplate.keys("*");
        for (String key : keys) {
            loginCodes.add((String) get(key));
        }
        return loginCodes;
    }


}
