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


    //查询所有文章信息，或根据用户查询文章信息
    @Select("<script> " +
            "select" +
            " a.item_id,a.id,a.create_time,a.title_name,a.read_numbers,a.change_time,a.source_text, " +
            "i.item_name,u.username create_name " +
            "from articles a inner join items i on a.item_id=i.id " +
            "INNER JOIN `user` u on u.id=a.create_user " +
            "WHERE a.delete_flag=0 " +
            "<if test='uid != null'> " +
            "and a.create_user=#{uid} " +
            "</if> " +
            "<if test='iid != null'> " +
            "and a.item_id=#{iid} " +
            "</if> " +
            "<if test='searchName != null'> " +
            "and a.title_name like #{searchName} " +
            "</if> " +
            "order by a.item_id,a.create_user " +
            "</script>")
    List<SysArticles> findArticlesInformation(@Param("uid") Integer uid,@Param("iid")Integer iid,@Param("searchName")String searchName);

    //更新文章信息
    @Update("update articles set source_text=#{source_text}," +
            "item_id=#{item_id},title_name=#{title_name},change_time=now() where id=#{id}")
    int changeArticleInformation(Articles articles);

    //更新文章内容
    @Update("update articles_main set md_text=#{md_text},html_text=#{html_text} where aid=#{id}")
    int changeArticleText(Articles articles);

    //保存文章信息
    @Insert("insert into articles set source_text=#{source_text},item_id=#{item_id}, " +
            "create_user=#{create_user},title_name=#{title_name},change_time=now(),create_time=now()")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addArticleInformation(Articles articles);

    //保存文章内容
    @Insert("insert into articles_main set md_text=#{md_text},html_text=#{html_text},aid=#{id}")
    int addArticleText(Articles articles);

    //根据文章ID查询文章
    @Select("select" +
            " a.id,a.item_id,a.title_name,a.create_user,a.read_numbers, " +
            "u.username create_name,a.change_time,am.md_text,am.html_text " +
            "from articles a inner join articles_main am on a.id=#{aid} and am.aid=#{aid} " +
            "inner join `user` u on u.id=a.create_user " +
            "WHERE a.delete_flag =0")
    SysArticles findArticlesByAid(Integer aid);




    /**
     * @param aid 文章id
     * @return: int
     * @author: 0205
     *
     * 通过文章id逻辑上删除文章
     */
    @Update("Update articles set delete_flag=1 where id=#{aid}")
    int delArticle(Integer aid);


    @Insert("insert into img_url set url=#{url},uid=#{uid},date=now()")
    int addImUrl(@Param("url") String url,@Param("uid") Integer uid);

    //根据文章查询用户
    @Select("select create_user from articles where id=#{aid}")
    int findAid(Integer aid);

    //根据文章ID查询所属条目ID
    @Select("select item_id from articles where id=#{aid}")
    int findIid(Integer aid);

    /**
     * @param uid  用户ID
     * @param iid  条目ID
     * @return: int
     * @author: 0205
     *
     * 查询用户在该条目的文章数目
     */
    @Select("select count(*) as a_num from articles where create_user=#{uid} and item_id=#{iid} and delete_flag=0")
    int findArticlesNum(@Param("uid")Integer uid,@Param("iid")Integer iid);

    /**
     * @param aid 文章ID
     * @param num 文章阅读次数增加数目
     * @return: void
     * @author: 0205
     *
     * 改变文章阅读次数
     */
    @Update("update articles set read_numbers=read_numbers+#{num} where id=#{aid}")
    void ChangeReadNums(@Param("aid") Integer aid,@Param("num") Integer num);





}
