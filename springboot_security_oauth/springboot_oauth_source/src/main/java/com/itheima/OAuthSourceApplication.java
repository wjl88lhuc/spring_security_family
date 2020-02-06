package com.itheima;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itheima.mapper")
public class OAuthSourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAuthSourceApplication.class,args);
    }
}