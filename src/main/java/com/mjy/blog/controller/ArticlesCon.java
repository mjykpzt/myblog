package com.mjy.blog.controller;

import com.mjy.blog.Bean.Articles;
import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.Role;
import com.mjy.blog.Service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author mjy
 * @create 2020-03-08-23:15
 */
@RestController
@RequestMapping("/articles")
public class ArticlesCon {
    @Autowired
    private ArticlesService articlesService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    @GetMapping()
    public ResponseBean finaAll(HttpServletRequest request,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(required = true) Integer pageSize) {
        List<Role> roles = (List<Role>)request.getAttribute("role");
        if (IsAdmin(roles)){
            return articlesService.findAll(pageNum, pageSize);
        }else {
            return articlesService.findArticles((Integer)request.getAttribute("uid"),pageNum,pageSize);
        }

    }

    @GetMapping(value = "/{uid}")
    public ResponseBean findArticles(@PathVariable Integer uid,
                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "5") Integer pageSize) {
        return articlesService.findArticles(uid,pageNum,pageSize);
    }

    @PostMapping("/addArticles")
    public ResponseBean addArticles(HttpServletRequest request, Articles articles) {
        articles.setCreate_user((Integer) request.getAttribute("uid"));
        return articlesService.addArticles(articles);
    }

    @PostMapping("/changeArticles")
    public ResponseBean changeArticles(Articles articles) {
        return articlesService.changeArticles(articles);
    }

    @GetMapping("/findArticlesByIid")
    public ResponseBean findArticlesByIid(@RequestParam(required = true) Integer iid,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "5") Integer pageSize) {

        return articlesService.findArticlesByIid(iid,pageNum,pageSize);
    }

    @GetMapping("/findArticlesByAid")
    public ResponseBean findArticlesByAid(@RequestParam(required = true) Integer aid) {
        return articlesService.findArticlesByAid(aid);
    }

    @PostMapping("/delArticle")
    public ResponseBean delArticle(@RequestParam(required = true) Integer aid, @RequestParam(required = true) Integer iid) {
        return articlesService.delArticle(aid, iid);
    }

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public ResponseBean uploadImg(HttpServletRequest req, MultipartFile image) {
        return articlesService.addImg(req, image);
    }


    private <T extends List<? extends Role>>Boolean IsAdmin(T t) {
        for (Role r : t){
            String role_name = r.getRole_name();
            if (role_name.contains("ROLE_ADMIN")){
                return true;
            }
        }
        return false;
    }



}

