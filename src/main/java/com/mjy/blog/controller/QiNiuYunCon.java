package com.mjy.blog.controller;

import com.mjy.blog.bean.ResponseBean;
import com.mjy.blog.service.QiNiuYunService;
import com.qiniu.util.Auth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/uploadImg")
public class QiNiuYunCon {
    @Resource
    private QiNiuYunService qiNiuYunService;

    @RequestMapping("/GetUploadToken")
    public ResponseBean Upload(String filename){
        return qiNiuYunService.uploadToken(filename);
    }

    @RequestMapping("/callback")
    public ResponseBean callback(HttpServletRequest request){
        return qiNiuYunService.callback(request);
    }

}
