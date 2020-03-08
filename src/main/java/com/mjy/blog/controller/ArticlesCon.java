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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseBean finaAll() {
        return articlesService.findAll();
    }

    @RequestMapping(value = "/{uid}",method = RequestMethod.GET)
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
}
