package com.mjy.blog.mapper;

import com.mjy.blog.Bean.Item;
import com.mjy.blog.Bean.SysItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author mjy
 * @create 2020-03-08-16:05
 */
public interface ItemDao {

    @Select("<script> " +
            "select" +
            " i.id,i.item_name,i.item_des,i.creat_time,i.creat_user,u.username creat_name,i.status,i.change_time " +
            "from `user` u,items i " +
            "where u.id=i.creat_user " +
            "<if test='uid != null'> " +
            "and i.creat_user=#{uid} " +
            "</if> " +
            "</script>")
    @ResultType(SysItem.class)
    List<SysItem> findItem(@Param("uid") Integer uid);


    @Insert("insert into items " +
            "set item_name=#{item_name},item_des=#{item_des},creat_user=#{uid},creat_time=now(),change_time=now()")
    int addItem(@Param("item_name")String name,@Param("item_des")String des,@Param("uid")Integer uid);


}
