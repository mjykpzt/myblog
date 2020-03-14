package com.mjy.blog.controller;

import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.User;
import com.mjy.blog.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author mjy
 * @create 2020-03-07-18:33
 */
@RestController
@RequestMapping("/user")
public class UserCon {
    @Autowired
    private UserService userService;
    @RequestMapping()
    public ResponseBean findAll(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "5") Integer pageSize){
        return userService.findAll(pageNum,pageSize);
    }

    @GetMapping("/{username}")
    public ResponseBean findByName(@PathVariable String username){
        return userService.findByName(username);
    }

    @PostMapping(value = "/adduser")
    public ResponseBean addUser(@RequestParam(required = true)String username,
                                @RequestParam(required = true)String password,
                                @RequestParam(required = true)String email
                                ){
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setEmail(email);
        return userService.addUser(user);
    }

    @PostMapping("/changeUserStatus")
    public ResponseBean changeUserStatus(@RequestParam(required = true) Integer userid,@RequestParam(required = true) Integer userstatus){
        return userService.changeUserStatus(userstatus, userid);
    }

    @GetMapping("/searchUser/{str}")
    public ResponseBean searchUser(@PathVariable String str){
        return userService.searchUser(str);
    }



}
