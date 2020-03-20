package com.mjy.blog.Service;

import com.mjy.blog.Bean.Articles;
import com.mjy.blog.Bean.ResponseBean;


/**
 * @author mjy
 * @create 2020-03-08-22:30
 */
public interface ArticlesService {
    ResponseBean findArticles(Integer uid);

    ResponseBean changeArticles(String text, String text_re,Integer id);

    ResponseBean addArticles(Articles articles);

    ResponseBean findAll();

}
