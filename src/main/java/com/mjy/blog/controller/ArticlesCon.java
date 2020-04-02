package com.mjy.blog.controller;

import com.mjy.blog.Bean.Articles;
import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mjy
 * @create 2020-03-08-23:15
 */
@RestController
@RequestMapping("/articles")
public class ArticlesCon {
    @Autowired
    private ArticlesService articlesService;

    @GetMapping()
    public ResponseBean finaAll() {
        return articlesService.findAll();
    }

    @GetMapping(value = "/{uid}")
    public ResponseBean findArticles(@PathVariable Integer uid) {
        return articlesService.findArticles(uid);
    }

    @PostMapping("/addArticles")
    public ResponseBean addArticles(HttpServletRequest request,Articles articles) {
        articles.setCreate_user((Integer)request.getAttribute("uid"));
        return articlesService.addArticles(articles);
    }

    @PostMapping("/changeArticles")
    public ResponseBean changeArticles(Articles articles) {
        return articlesService.changeArticles(articles);
    }

    @GetMapping("/findArticlesByIid")
    public ResponseBean findArticlesByIid(@RequestParam(required = true) Integer iid) {

        return articlesService.findArticlesByIid(iid);
    }

    @GetMapping("/findArticlesByAid")
    public ResponseBean findArticlesByAid(@RequestParam(required = true) Integer aid) {
        return articlesService.findArticlesByAid(aid);
    }

    @PostMapping("/delArticle")
    public ResponseBean delArticle(@RequestParam(required = true)Integer aid,@RequestParam(required = true)Integer iid){
        return articlesService.delArticle(aid,iid);
    }


}

