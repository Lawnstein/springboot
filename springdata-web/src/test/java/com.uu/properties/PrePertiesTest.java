package com.uu.properties;

import com.uu.utils.NeoProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述：
 *
 * @author uusao
 * @create 2018-03-16 17:58
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class PrePertiesTest {
    @Autowired
    private NeoProperties neoProperties;


    @Test
    public void getHello() throws Exception {
        System.out.println(neoProperties.getTitle());
        //Assert.assertEquals(neoProperties.getTitle(), "纯洁的微笑");
        //Assert.assertEquals(neoProperties.getDescription(), "分享生活和技术");
    }

}
