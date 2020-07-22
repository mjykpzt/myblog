package com.mjy.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mjy.blog.bean.Articles;
import com.mjy.blog.bean.ResponseBean;
import com.mjy.blog.bean.SysArticles;
import com.mjy.blog.mapper.ArticlesDao;
import com.mjy.blog.mapper.ItemDao;
import com.mjy.blog.service.ArticlesService;
import com.mjy.blog.utils.ArticlesXssFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mjy
 * @create 2020-03-08-22:55
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor =Exception.class )
public class ArticlesServiceImpl implements ArticlesService {

    @Resource
    private ArticlesDao articlesDao;

    @Resource
    private ItemDao itemDao;


    @Override
    public ResponseBean findArticlesInformation(Integer uid, Integer iid, String searchName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysArticles> articles = articlesDao.findArticlesInformation(uid, iid, searchName);
        PageInfo<SysArticles> sysArticlesPageInfo = new PageInfo<>(articles);
        return ResponseBean.getSuccessResponse("查询成功", sysArticlesPageInfo);
    }

    @Override
    public ResponseBean changeArticles(Articles articles) {
        if (!itemDao.isCanUse(articles.getItem_id())) {
            return ResponseBean.getFailResponse("该条目已被禁用");
        }
        int OldIid = articlesDao.findIid(articles.getId());
        if (OldIid != articles.getItem_id()) {
            itemDao.addNumber(articles.getItem_id());
            itemDao.subNumber(OldIid);
        }

        Articles articles_safe =  ArticlesXssFilter.ArticlesXss(articles);

        String s = articles_safe.getHtml_text();
        articles.setSource_text(s.substring(0, Math.min(s.length(), 50)));

        int i = articlesDao.changeArticleInformation(articles);
        int i1 = articlesDao.changeArticleText(articles);
        if (i > 0 && i1 > 0) {
            return ResponseBean.getSuccessResponse("改变成功");
        }
        return ResponseBean.getFailResponse("改变失败");
    }

    @Override
    public ResponseBean addArticles(Articles articles) {
        if (!itemDao.isCanUse(articles.getItem_id())) {
            return ResponseBean.getFailResponse("该条目已被禁用");
        }

        Articles articles_safe = ArticlesXssFilter.ArticlesXss(articles);

        String s = articles_safe.getHtml_text();
        articles.setSource_text(s.substring(0, Math.min(s.length(), 50)));
        int i = articlesDao.addArticleInformation(articles);
        int i1 = articlesDao.addArticleText(articles);
        Integer id = articles.getItem_id();
        itemDao.addNumber(id);
        if (i > 0 && i1 > 0) {
            return ResponseBean.getSuccessResponse("添加文章成功");
        }
        return ResponseBean.getFailResponse("添加文章失败");
    }


    @Override
    public ResponseBean findArticlesByAid(Integer aid) {
        SysArticles articles = articlesDao.findArticlesByAid(aid);
        if (articles != null) {
            articlesDao.ChangeReadNums(aid, 1);
            return ResponseBean.getSuccessResponse("查询成功", articles);
        }
        return ResponseBean.getFailResponse("查询失败");
    }

    @Override
    public ResponseBean delArticle(Integer aid, Integer iid) {
        int i = articlesDao.delArticle(aid);
        itemDao.subNumber(iid);
        if (i > 0) {
            return ResponseBean.getSuccessResponse("删除成功");
        }
        return ResponseBean.getFailResponse("删除失败");
    }


    @Override
    public int findAid(Integer aid) {
        return articlesDao.findAid(aid);
    }

}
