package com.uu.web;

import com.uu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述：
 *
 * @author uusao
 * @create 2018-03-15 15:19
 **/
@Controller
public class UserController {

    @Value("${com.neo.title}")
    private String title;
    @Value("${com.neo.description}")
    private String description;


    @Autowired
    private User user;

    @RequestMapping("hello")
    @ResponseBody
    public String hello() {
        return title + description;
    }

    @RequestMapping(value = "/chartjsp")
    public String hello1() {
        return "chartjsp";
    }

}
