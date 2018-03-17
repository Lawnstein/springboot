package com.uu.web;

import com.uu.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 描述：
 *
 * @author uusao
 * @create 2018-03-16 10:52
 **/
@RestController
public class UserRestController {

    @Value("${com.neo.title}")
    private String title;
    @Value("${com.neo.description}")
    private String description;

    @RequestMapping("/getUser")
    public User getUser(){
        User user = new User();
        user.setAge(2);
        user.setName("呵呵呵");
        user.setSex(1);

        return user;
    }

    @RequestMapping("/getProperties")
    public String getProperties(){
        return description +"...." + title;
    }

    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
}
