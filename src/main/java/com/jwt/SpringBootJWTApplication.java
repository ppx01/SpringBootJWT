package com.jwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jingdeyue
 * @date 2023/1/31 18:04
 */
@SpringBootApplication
@MapperScan(basePackages = "com.jwt.dao")
public class SpringBootJWTApplication {

    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(SpringBootJWTApplication.class, args);
    }
}
