package com.uu.husky.controller;

import com.uu.husky.dao.UserDao;
import com.uu.husky.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-07-23 下午5:05
 **/
@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @GetMapping(value = "/users")
    public HttpEntity<Object> getUsers(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<User> page = userDao.findAll(pageable);

        List<User> users = page.getContent();

        return new ResponseEntity<>(users,HttpStatus.OK);
    }

   /* @RequestMapping(value = "/users2", method = RequestMethod.GET)
    HttpEntity<PagedResourcesAssembler<User>> persons(Pageable pageable,
                                                      PagedResourcesAssembler<User> assembler) {

        Page<User> persons = userDao.findAll(pageable);
        return new ResponseEntity<User>(assembler.toResource(persons), HttpStatus.OK);
    }*/

    /*@RequestMapping(value = "/user3", method = RequestMethod.GET)
    String index(Model model, @QuerydslPredicate(root = User.class) Predicate predicate,
                 Pageable pageable, @RequestParam MultiValueMap<String, String> parameters) {

        model.addAttribute("users", userDao.findAll(predicate, pageable));

        return "index";
    }*/


}
