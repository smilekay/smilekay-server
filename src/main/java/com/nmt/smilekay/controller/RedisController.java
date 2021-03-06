package com.nmt.smilekay.controller;

import com.nmt.smilekay.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
@RestController
public class RedisController {
    private static final String RESULT_OK = "ok";

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "put", method = RequestMethod.POST)
    public String set(String key, String value, long seconds) {
        redisService.put(key, value, seconds);
        return RESULT_OK;
    }

    @RequestMapping(value = "find", method = RequestMethod.GET)
    public String find(String key) {
        String json = null;
        Object obj = redisService.get(key);
        if (obj != null) {
            json = (String) redisService.get(key);
        }
        return json;
    }
}
