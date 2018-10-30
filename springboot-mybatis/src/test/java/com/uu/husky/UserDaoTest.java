package com.uu.husky;

import com.uu.ApplicationStart;
import com.uu.dao.UserMapper;
import com.uu.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 * @author uusao
 * @create 2018-03-19 11:12
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationStart.class)
public class UserDaoTest {

    @Autowired
    private UserMapper userDao;

    @Test
    public void testInsert() {
        User user = new User();
        user.setAges(1);
        user.setPassword("不知道1");

        User user1 = new User();
        user1.setAges(1);
        user1.setPassword("不知道1");


        List<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user1);
        int i = userDao.batchSave(users);

    }
}
