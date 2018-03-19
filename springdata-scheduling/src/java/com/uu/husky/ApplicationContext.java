package com.uu.husky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 描述：
 *
 * @author uusao
 * @create 2018-03-19 16:40
 **/
@SpringBootApplication
@EnableScheduling
public class ApplicationContext {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationContext.class, args);
    }

}
