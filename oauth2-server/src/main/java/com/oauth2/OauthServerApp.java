package com.oauth2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.oauth2.mapper")
public class OauthServerApp {
    public static void main(String[] args) {
        SpringApplication.run(OauthServerApp.class, args);
    }
}