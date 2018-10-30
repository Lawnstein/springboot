package com.uu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-04-01 22:41
 **/
@SpringBootApplication
@MapperScan("com.uu.dao")
public class ApplicationStart {

   public static void main(String[] args) {
       SpringApplication.run(ApplicationStart.class, args);
   }

}
