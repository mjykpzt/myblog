package com.mjy.blog.mapper;

import com.mjy.blog.Bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author mjy
 * @create 2020-03-07-18:31
 */
public interface UserDao {
    //查询所有用户
    @Select("select * from user")
    @ResultMap("t1")
    List<User> findAll();

    //通过用户名查找用户
    @Select("<script>"+"select * from user where 1=1 " +
            "<if test='!flag'> " +
            "and username=#{username} " +
            "</if> " +
            "<if test='flag'> " +
            "and username like #{username} " +
            "</if> " +
            "</script>")
    @Results(id="t1",value = {
            @Result(column = "id",property = "roles",javaType = java.util.List.class,
                    many = @Many(select="com.mjy.blog.mapper.RoleDao.findRoleNameByUid")
            )
    })
    List<User> findUserByName(@Param("username") String username,@Param("flag")Boolean flag);

    //添加用户
    @Insert("insert into user " +
            "set username=#{username},password=#{password},email=#{email},creat_time=now()")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int addUser(User user);

    //添加用户的角色
    @Insert("insert into role_and_user " +
            "set uid=#{uid},rid=#{rid}")
    int setUserRoles(@Param("uid") Integer uid,@Param("rid") Integer rid);


    //改变用户账号状态
    @Update("update user set status =#{status} where id=#{uid}")
    int changeUserStatus(@Param("status")Integer status,@Param("uid")Integer uid);

}
