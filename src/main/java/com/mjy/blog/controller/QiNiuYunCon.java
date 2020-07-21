package com.mjy.blog.controller;

import com.mjy.blog.bean.ResponseBean;
import com.mjy.blog.service.QiNiuYunService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
/**
 *@author mjy
 *@date 2020/7/19
 */
@RestController
@RequestMapping("/uploadImg")
public class QiNiuYunCon {
    @Resource
    private QiNiuYunService qiNiuYunService;

    @RequestMapping("/GetUploadToken")
    public ResponseBean upload(String filename){
        return qiNiuYunService.uploadToken(filename,1);
    }


    @RequestMapping("/callback")
    public ResponseBean callback(HttpServletRequest request){
        return qiNiuYunService.callback(request);
    }

}
