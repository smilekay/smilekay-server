package com.nmt.smilekay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = {"com.nmt.smilekay.mapper"})
public class SmilekayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmilekayServerApplication.class, args);
    }

}
