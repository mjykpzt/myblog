package com.mjy.blog.controller;

import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.mapper.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mjy
 * @create 2020-05-09-0:01
 */
@RestController
@RequestMapping("/test")
public class TestCon {
    @Autowired
    private TestDao testDao;
    @RequestMapping("/test1")
    public ResponseBean test1(){
        return ResponseBean.getSuccessResponse("1",testDao.t());
    }
}
