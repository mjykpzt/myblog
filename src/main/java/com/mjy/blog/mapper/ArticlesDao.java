package com.mjy.blog.mapper;

import com.mjy.blog.Bean.Articles;
import com.mjy.blog.Bean.SysArticles;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author mjy
 * @create 2020-03-08-21:23
 */
public interface ArticlesDao {


    //查询所有文章，或根据用户查询文章
    @Select("<script> " +
            "select" +
            " a.id,a.item_id,a.title_name,a.create_time,a.create_user,u.username create_name, " +
            "a.status,a.change_time,a.md_text,a.source_text,i.item_name,a.html_text,a.read_numbers " +
            "from `user` u,articles a,items i " +
            "where u.id=a.create_user and a.item_id=i.id and a.delete_flag=0" +
            "<if test='uid != null'> " +
            "and a.create_user=#{uid} " +
            "</if> " +
            "<if test='iid != null'> " +
            "and a.item_id=#{iid} " +
            "</if> " +
            "<if test='searchName != null'> " +
            "and a.title_name like #{searchName} " +
            "</if> " +
            "</script>")
    List<SysArticles> findArticles(@Param("uid") Integer uid,@Param("iid")Integer iid,@Param("searchName")String searchName);

    //更新文章内容
    @Update("update articles set md_text=#{md_text},source_text=#{source_text}," +
            "item_id=#{item_id},html_text=#{html_text},title_name=#{title_name},change_time=now() where id=#{id}")
    int changeArticles(Articles articles);

    //保存文章
    @Insert("insert into articles set md_text=#{md_text},source_text=#{source_text},html_text=#{html_text},item_id=#{item_id}, " +
            "create_user=#{create_user},title_name=#{title_name},change_time=now(),create_time=now()")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addArticles(Articles articles);

    //根据文章ID查询文章
    @Select("select" +
            " a.id,a.item_id,a.title_name,a.create_time,a.create_user,a.read_numbers, " +
            "u.username create_name,a.status,a.change_time,a.md_text,a.source_text,a.html_text,i.item_name" +
            " from `user` u,articles a,items i " +
            "where u.id=i.create_user and a.item_id=i.id and a.id=#{aid}")
    SysArticles findArticlesByAid(Integer aid);

    //删除文章
    @Update("Update articles set delete_flag=1 where id=#{aid}")
    int delArticle(Integer aid);

    //记录图片链接
    @Insert("insert into img_url set url=#{url},uid=#{uid},date=now()")
    int addImUrl(@Param("url") String url,@Param("uid") Integer uid);

    //根据文章查询用户
    @Select("select create_user from articles where id=#{aid}")
    int findAid(Integer aid);

    //查询文章数量
    @Select("select count(id) as a_num from articles where create_user=#{uid} and item_id=#{iid} and delete_flag=0")
    int findArticlesNum(@Param("uid")Integer uid,@Param("iid")Integer iid);

    //阅读次数增加
    @Update("update articles set read_numbers=read_numbers+1 where id=#{aid}")
    void addReadNums(Integer aid);




}
