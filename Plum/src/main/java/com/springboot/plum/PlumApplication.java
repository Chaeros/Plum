package com.springboot.plum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // redis 사용을 위해 추가할 부분(인 메모리 사용)
public class PlumApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlumApplication.class, args);
    }

}
