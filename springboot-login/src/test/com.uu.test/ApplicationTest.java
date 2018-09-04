package com.uu.test;

import com.neo.JpaThymeleafApplication;
import com.neo.config.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-09-04 上午11:01
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaThymeleafApplication.class)
public class ApplicationTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Test
    public void insertDate() {
        redisTemplate.opsForValue().set("liushishi", "不错啊", Constants.TOKEN_EXPIRES_MINUTE, TimeUnit.MINUTES);
    }

    @Test
    public void getData() {
        String token = redisTemplate.boundValueOps("yangmi").get().toString();
        System.out.println(token);
    }



}
