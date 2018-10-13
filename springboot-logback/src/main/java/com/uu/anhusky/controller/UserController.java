package com.uu.anhusky.controller;

import com.uu.anhusky.config.annotation.ControllerLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2017-10-30 14:20
 **/
@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    /**
     * http://127.0.0.1:8080/index/?content="我是测试内容"
     *
     * @param userName
     * @param password
     * @return
     */
    @ControllerLog("登录")
    @PostMapping(value = "/login")
    public String login(String userName, String password) throws Exception {
        if (!"huge".equals(userName) || !"123".equals(password)) {
            log.error("登录失败，用户名:{} - 密码:{}", userName, password);
            return "用户名或者密码不正确";
            //throw new Exception("用户名或者密码不正确");
        }
        return "登录成功";
    }
}
