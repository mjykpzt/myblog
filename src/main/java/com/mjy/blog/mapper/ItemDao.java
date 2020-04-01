package com.mjy.blog.mapper;

import com.mjy.blog.Bean.Item;
import com.mjy.blog.Bean.SysItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author mjy
 * @create 2020-03-08-16:05
 */
public interface ItemDao {
    //查询所有条目，或根据用户查询条目
    @Select("<script> " +
            "select" +
            " i.id,i.item_name,i.item_des,i.create_time,i.create_user,u.username create_name,i.status,i.change_time " +
            "from `user` u,items i " +
            "where u.id=i.create_user ORDER BY id" +
            "<if test='uid != null'> " +
            "and i.create_user=#{uid} ORDER BY id" +
            "</if> " +
            "</script>")
    @ResultType(SysItem.class)
    List<SysItem> findItem(@Param("uid")Integer uid);

    //保存条目
    @Insert("insert into items " +
            "set item_name=#{item_name},item_des=#{item_des},create_user=#{uid},create_time=now(),change_time=now()")
    int addItem(@Param("item_name")String name,@Param("item_des")String des,@Param("uid")Integer uid);

    //更新条目状态（条目是否可用）
    @Update("update items set status =#{status},change_time=now() where id=#{id}")
    int changeItemStatus(@Param("status")Short status,@Param("id")Integer id);

    //更新条目（条目内容）
    @Update("update items set item_name =#{name},item_des=#{des},change_time=now() where id=#{id}")
    int changeItem(@Param("name")String name,@Param("des")String des,@Param("id")Integer id);

    //查询条目根据条目ID
    @Select("select id,item_name,item_des from items where id=#{iid}")
    Item findItemByIid(Integer iid);

    @Select("select COUNT(*) from items where item_name=#{name} and #{random}= #{random}")
    int findIsHasName(String name,double random);

    @Select("select id from items where item_name=#{name}")
    int findIdByName(String name);

}
