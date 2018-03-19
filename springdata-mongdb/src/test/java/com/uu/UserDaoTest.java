package com.uu;

import com.uu.husky.ApplicationStart;
import com.uu.husky.dao.UserDao;
import com.uu.husky.domain.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 描述：
 *
 * @author uusao
 * @create 2018-03-19 18:56
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationStart.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testSaveUser() throws Exception {
        for (int i = 0; i < 60; i++) {
            UserEntity user=new UserEntity();
            user.setId((long) i);
            user.setUserName("员工 " + i);
            user.setPassWord("哈哈哈" + i);
            userDao.saveUser(user);
        }

    }

    @Test
    public void findUserByUserName(){
        UserEntity user= userDao.findUserByUserName("小明");
        System.out.println("user is "+user);
    }

    @Test
    public void updateUser(){
        UserEntity user=new UserEntity();
        user.setId(2l);
        user.setUserName("天空");
        user.setPassWord("fffxxxx");
        userDao.updateUser(user);
    }

    @Test
    public void deleteUserById(){
        userDao.deleteUserById(1l);
    }

    @Test
    public void findUserByPage(){
        List<UserEntity> users = userDao.findUserByPage(2, 5);
        System.out.println(users.toString());
    }

    @Test
    public void findUserByGroup(){
        String [] arrayNames = {"passWord"};
        List<UserEntity> users = userDao.findUserByGroup(arrayNames, null);
        System.out.println(users.toString());
    }

}
