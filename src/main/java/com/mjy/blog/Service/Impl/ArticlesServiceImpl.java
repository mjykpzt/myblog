package com.mjy.blog.Service.Impl;

import com.mjy.blog.Bean.Articles;
import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.SysArticles;
import com.mjy.blog.Service.ArticlesService;
import com.mjy.blog.mapper.ArticlesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mjy
 * @create 2020-03-08-22:55
 */
@Service
@Transactional
public class ArticlesServiceImpl implements ArticlesService {
    @Autowired
    private ArticlesDao articlesDao;

    @Override
    @Transactional(readOnly = true,isolation = Isolation.READ_COMMITTED )
    public ResponseBean findAll() {
        List<SysArticles> articles = articlesDao.findArticles(null);
        if (articles != null){
            return ResponseBean.getSuccessResponse("查询成功",articles);
        }
        return ResponseBean.getFailResponse("查询失败");
    }

    @Override
    public ResponseBean findArticles(Integer uid) {
        List<SysArticles> articles = articlesDao.findArticles(uid);
        if (articles != null){
            return ResponseBean.getSuccessResponse("查询成功",articles);
        }
        return ResponseBean.getFailResponse("查询失败");
    }

    @Override
    public ResponseBean changeArticles(String text, String text_re,Integer id) {
        int i = articlesDao.changeArticles(text,text_re,id);
        if (i>0){
            return ResponseBean.getSuccessResponse("改变成功");
        }
        return ResponseBean.getFailResponse("改变失败");
    }

    @Override
    public ResponseBean addArticles(Articles articles) {
        int i = articlesDao.addArticles(articles);
        if (i>0){
            return ResponseBean.getSuccessResponse("改变成功");
        }
        return ResponseBean.getFailResponse("改变失败");
    }

    @Override
    @Transactional(readOnly = true,isolation = Isolation.READ_COMMITTED )
    public ResponseBean findArticlesByIid(Integer iid) {
        List<SysArticles> articles = articlesDao.findArticlesByIid(iid);
        if (articles != null && articles.size()>0){
            return ResponseBean.getSuccessResponse("查询成功",articles);
        }
        return ResponseBean.getFailResponse("查询失败");
    }

    @Override
    @Transactional(readOnly = true,isolation = Isolation.READ_COMMITTED )
    public ResponseBean findArticlesByAid(Integer aid) {
        SysArticles articles = articlesDao.findArticlesByAid(aid);
        if (articles != null){
            return ResponseBean.getSuccessResponse("查询成功",articles);
        }
        return ResponseBean.getFailResponse("查询失败");
    }
}
