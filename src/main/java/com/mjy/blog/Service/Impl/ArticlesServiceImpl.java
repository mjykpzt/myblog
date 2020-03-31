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
@Transactional(isolation =Isolation.READ_COMMITTED)
public class ArticlesServiceImpl implements ArticlesService {
    @Autowired
    private ArticlesDao articlesDao;

    @Override
    @Transactional(readOnly = true)
    public ResponseBean findAll() {
        List<SysArticles> articles = articlesDao.findArticles(null);
        if (articles != null){
            return ResponseBean.getSuccessResponse("查询成功",articles);
        }
        return ResponseBean.getFailResponse("查询失败");
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseBean findArticles(Integer uid) {
        List<SysArticles> articles = articlesDao.findArticles(uid);
        if (articles != null){
            return ResponseBean.getSuccessResponse("查询成功",articles);
        }
        return ResponseBean.getFailResponse("查询失败");
    }

    @Override
    public ResponseBean changeArticles(Articles articles) {
//        article.setSummary(stripHtml.substring(0, stripHtml.length() > 50 ? 50 : stripHtml.length()));
        String s = stripHtml(articles.getHtml_text());
        articles.setSource_text(s.substring(0, s.length() > 50 ? 50 : s.length()));

        int i = articlesDao.changeArticles(articles);
        if (i>0){
            return ResponseBean.getSuccessResponse("改变成功");
        }
        return ResponseBean.getFailResponse("改变失败");
    }

    @Override
    public ResponseBean addArticles(Articles articles) {
        String s = stripHtml(articles.getHtml_text());
        articles.setSource_text(s.substring(0, s.length() > 50 ? 50 : s.length()));

        int i = articlesDao.addArticles(articles);
        if (i>0){
            return ResponseBean.getSuccessResponse("添加文章成功");
        }
        return ResponseBean.getFailResponse("添加文章失败");
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

    @Override
    public ResponseBean delArticle(Integer id) {
        int i = articlesDao.delArticle(id);
        if (i>0){
            return ResponseBean.getSuccessResponse("删除成功");
        }
        return ResponseBean.getFailResponse("删除失败");
    }

    public String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }
}
