package com.uu.husky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 描述：
 * 启动类
 * @author uusao
 * @create 2018-03-15 15:17
 **/
@SpringBootApplication
//@Configuration
@ComponentScan
public class ApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }
}
