package com.uu.husky;

import com.uu.husky.dao.UserDao;
import com.uu.husky.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述：
 *
 * @author uusao
 * @create 2018-03-19 11:12
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testInsert(){
        User user = new User();
        user.setId(13l);
        user.setAge(12);
        user.setNickName("小红");
        user.setEmail("@16.com");
        user.setRegTime("dd");
        userDao.insertSelective(user);
    }
}
