package com.mjy.blog.service;

import com.mjy.blog.bean.Articles;
import com.mjy.blog.bean.ResponseBean;


/**
 * @author mjy
 * @create 2020-03-08-22:30
 */
public interface ArticlesService {

    /**
     *
     *查找文章信息
     *
     * @param uid 用户id
     * @param iid 条目id
     * @param searchName 搜索关键字
     * @param pageNum 页数
     * @param pageSize 页面条目数
     * @return: com.mjy.blog.bean.ResponseBean
     * @author: 0205
     */
    ResponseBean findArticlesInformation(Integer uid,Integer iid,String searchName,Integer pageNum,Integer pageSize);

    /**
     *
     *改变文章内容
     *
     * @param articles 文章
     * @return: com.mjy.blog.bean.ResponseBean
     * @author: 0205
     */
    ResponseBean changeArticles(Articles articles);


    /**
     *
     *添加文章
     *
     * @param articles 文章
     * @return: com.mjy.blog.bean.ResponseBean
     * @author: 0205
     */
    ResponseBean addArticles(Articles articles);

    /**
     *
     *根据文章id搜索文章
     *
     * @param aid 文章id
     * @return: com.mjy.blog.bean.ResponseBean
     * @author: 0205
     */
    ResponseBean findArticlesByAid(Integer aid);

    /**
     *
     *根据文章id删除文章，并把文章从对应条目移除
     *
     * @param aid 文章id
     * @param iid 条目id
     * @return: com.mjy.blog.bean.ResponseBean
     * @author: 0205
     */
    ResponseBean delArticle(Integer aid,Integer iid);

    /**
     *
     *根据文章id查询文章是否存在
     *
     * @param aid 文章id
     * @return: int
     * @author: 0205
     */
    int findAid(Integer aid);

}
