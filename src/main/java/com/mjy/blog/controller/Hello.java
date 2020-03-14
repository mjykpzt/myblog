package com.mjy.blog.controller;

import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.User;
import com.mjy.blog.Service.UserService;
import com.mjy.blog.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author mjy
 * @create 2020-03-07-18:16
 */
@RestController
@RequestMapping("/hello")
public class Hello {
    @Autowired
    private UserDao userDao;
    @PostMapping()
    public ResponseBean hello(@RequestParam(required = true) String username,@RequestParam(required = true) String password,
                              HttpServletResponse response){

        List<User> user = userDao.findUserByName(username, false);
        if (user !=null && user.size()!=0){
            if (user.get(0).getPassword().equals(password)){
                String token="fgyuhfpsiuhfsuphifuspiph";
                response.addHeader("Authorization", "Bearer " + token);
                return ResponseBean.getSuccessResponse("登陆成功");
            }
        }
        return ResponseBean.getFailResponse("账号或密码错误");

    }
}
