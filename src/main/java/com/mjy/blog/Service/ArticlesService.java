package com.mjy.blog.Service;

import com.mjy.blog.Bean.Articles;
import com.mjy.blog.Bean.ResponseBean;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * @author mjy
 * @create 2020-03-08-22:30
 */
public interface ArticlesService {
    ResponseBean findArticles(Integer uid,Integer pageNum,Integer pageSize);

    ResponseBean changeArticles(Articles articles);

    ResponseBean addArticles(Articles articles);

    ResponseBean findAll(Integer pageNum,Integer pageSize);

    ResponseBean findArticlesByIid(Integer iid,Integer pageNum,Integer pageSize);

    ResponseBean findArticlesByAid(Integer aid);

    ResponseBean delArticle(Integer aid,Integer iid);

    ResponseBean addImg(HttpServletRequest req, MultipartFile image);



}
