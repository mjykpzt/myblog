package com.mjy.blog.controller;

import com.mjy.blog.Bean.Articles;
import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseBean addArticles(Articles articles) {
        return articlesService.addArticles(articles);
    }

    @PostMapping("/changeArticles")
    public ResponseBean changeArticles(@RequestParam(required = true) String text, @RequestParam(required = true) String text_re,
                                       @RequestParam(required = true) Integer id) {
        return articlesService.changeArticles(text, text_re, id);
    }

    @GetMapping("/findArticlesByIid")
    public ResponseBean findArticlesByIid(@RequestParam(required = true)Integer iid){
        return articlesService.findArticlesByIid(iid);
    }

    @GetMapping("/findArticlesByAid")
    public ResponseBean findArticlesByAid(@RequestParam(required = true)Integer aid){
        return articlesService.findArticlesByAid(aid);
    }
}

