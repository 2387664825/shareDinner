package com.hfy.dinner;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hfy.dinner.dao")
public class DinnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DinnerApplication.class, args);
    }

}
