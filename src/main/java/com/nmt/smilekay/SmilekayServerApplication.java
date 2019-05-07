package com.nmt.smilekay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.nmt.smilekay.mapper")
public class SmilekayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmilekayServerApplication.class, args);
    }

}
