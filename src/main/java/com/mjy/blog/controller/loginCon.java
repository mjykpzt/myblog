package com.mjy.blog.controller;

import com.mjy.blog.Bean.ResponseBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mjy
 * @create 2020-03-07-18:16
 *
 *
 * 模拟登陆
 */
@RestController
public class loginCon {
    @RequestMapping("/login_error")
    public ResponseBean loginError(){
        return ResponseBean.getFailResponse("登陆失败");
    }

    @RequestMapping("/login_success")
    public ResponseBean loginSuccess(){
        return ResponseBean.getSuccessResponse("登录成功");
    }

    @RequestMapping("/login_page")
    public ResponseBean loginPage(){
        return ResponseBean.getFailResponse("请先登录");
    }
}
