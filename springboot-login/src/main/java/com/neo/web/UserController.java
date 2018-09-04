package com.neo.web;

import com.neo.auth.annotation.Authorization;
import com.neo.auth.annotation.CurrentUser;
import com.neo.auth.manager.TokenManager;
import com.neo.auth.model.TokenModel;
import com.neo.entity.User;
import com.neo.model.ResultModel;
import com.neo.model.ResultStatus;
import com.neo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager redisTokenManager;

    @RequestMapping("/")
    public String index() {
        return "user/login";
    }

    @PostMapping("/doLogin")
    @ResponseBody
    public ResponseEntity<Object> doLogin(String username, String password, HttpServletResponse response) {

        HashMap<String, Object> map = new HashMap<>();
        if (username.equals("huge") && password.equals("1")) {

            //获取userID
            User user = userService.findUserByUserName(username);
            TokenModel model = redisTokenManager.createToken(user.getId());

           /* String data = JSONObject.toJSONString(model);
            System.out.println(data);

            Cookie cookie = new Cookie("sessionId", username);
            cookie.setMaxAge(30 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);*/


            map.put("success", "true");
            map.put("data", model);
            return new ResponseEntity<Object>(ResultModel.ok(model), HttpStatus.OK);
        } else {
            map.put("success", "false");

            return new ResponseEntity<Object>(ResultModel.error(ResultStatus.ARGS_ERROR), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list")
    @ResponseBody
    @Authorization
    public ResponseEntity<Object> list( @CurrentUser User user) {
        if (user != null) {
            System.out.println("用户数据注入成功：" + user.toString());
        }

        List<User> users = userService.getUserList();
        return  new ResponseEntity<Object>(ResultModel.ok(users), HttpStatus.OK);
    }


    //-------------------------------------------  ----------------------------------------------

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "user/userAdd";
    }

    @RequestMapping("/add")
    public String add(User user) {
        userService.save(user);
        return "redirect:/list";
    }

    @RequestMapping("/toEdit")
    public String toEdit(Model model, String id) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user/userEdit";
    }

    @RequestMapping("/edit")
    public String edit(User user) {
        userService.edit(user);
        return "redirect:/list";
    }


    @RequestMapping("/delete")
    public String delete(String id) {
        userService.delete(id);
        return "redirect:/list";
    }
}
