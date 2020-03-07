package com.mjy.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mjy
 * @create 2020-03-07-18:16
 */
@RestController
//@RequestMapping("/hello")
public class Hello {
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
