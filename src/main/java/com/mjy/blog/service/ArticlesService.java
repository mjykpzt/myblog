package com.mjy.blog.service;

import com.mjy.blog.bean.Articles;
import com.mjy.blog.bean.ResponseBean;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * @author mjy
 * @create 2020-03-08-22:30
 */
public interface ArticlesService {
    ResponseBean findArticlesInformation(Integer uid,Integer iid,String searchName,Integer pageNum,Integer pageSize);

    ResponseBean changeArticles(Articles articles);

    ResponseBean addArticles(Articles articles);

    ResponseBean findArticlesByAid(Integer aid);

    ResponseBean delArticle(Integer aid,Integer iid);

    int findAid(Integer aid);

    //11111
}
