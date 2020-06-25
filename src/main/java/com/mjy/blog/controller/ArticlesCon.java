package com.mjy.blog.controller;

import com.mjy.blog.Bean.Articles;
import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.Role;
import com.mjy.blog.Service.ArticlesService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author mjy
 * @create 2020-03-08-23:15
 */
@RestController
@RequestMapping("/articles")
//@Validated
public class ArticlesCon {
    @Resource
    private ArticlesService articlesService;


    @GetMapping("/findArticles")
    public ResponseBean findArticles(HttpServletRequest request,
                                     @RequestParam(required = false) Integer iid,
                                     @RequestParam(required = false)String searchName,
                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "5") Integer pageSize) {
        if (!(searchName.length() > 0)){
            searchName=null;
        }else {
            searchName="%"+searchName+"%";
        }

        if (IsAdmin(request)){
            return articlesService.findArticlesInformation(null,iid,searchName,pageNum,pageSize);
        }else {
            return articlesService.findArticlesInformation((Integer)request.getAttribute("uid"),iid,searchName,pageNum,pageSize);
        }
    }


    @PostMapping("/addArticles")
    public ResponseBean addArticles(HttpServletRequest request,@Validated Articles articles) {
        articles.setCreate_user((Integer) request.getAttribute("uid"));
        return articlesService.addArticles(articles);
    }


    @PostMapping("/changeArticles")
    public ResponseBean changeArticles(HttpServletRequest request,@Validated Articles articles) {
        if ((Integer)request.getAttribute("uid")==articlesService.findAid(articles.getId())){
            return articlesService.changeArticles(articles);
        }
        return ResponseBean.getFailResponse("权限不足");
    }


    @GetMapping("/findArticlesByAid")
    public ResponseBean findArticlesByAid(HttpServletRequest request,@RequestParam() Integer aid) {
        if (IsAdmin(request)||(Integer)request.getAttribute("uid")==articlesService.findAid(aid)){
            return articlesService.findArticlesByAid(aid);
        }else {
            return ResponseBean.getFailResponse("权限不足");

        }
    }


    @PostMapping("/delArticle")
    public ResponseBean delArticle(HttpServletRequest request,@RequestParam() Integer aid, @RequestParam() Integer iid) {
        if (IsAdmin(request)||(Integer)request.getAttribute("uid")==articlesService.findAid(aid)){
            return articlesService.delArticle(aid, iid);
        }else {
            return ResponseBean.getFailResponse("权限不足");

        }

    }


    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public ResponseBean uploadImg(HttpServletRequest req, MultipartFile image) {
        return articlesService.addImg(req, image);
    }


    private Boolean IsAdmin(HttpServletRequest request) {
        List<Role> roles = (List<Role>) request.getAttribute("role");
        for (Role r : roles){
            String role_name = r.getRole_name();
            if (role_name.contains("ROLE_ADMIN")){
                return true;
            }
        }
        return false;
    }



}

