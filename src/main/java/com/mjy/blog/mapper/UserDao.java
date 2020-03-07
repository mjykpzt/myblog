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
    @Select("select * from user where username=#{username}")
    @Results(id="t1",value = {
            @Result(column = "id",property = "roles",javaType = java.util.List.class,
                    many = @Many(select="com.mjy.blog.mapper.RoleDao.findRoleNameByUid")
            )
    })
    User findUserByName(String username);

    //添加用户
    @Insert("insert into user " +
            "set username=#{username},password=#{password},email=#{email},creat_time=now()")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int addUser(User user);

    //添加用户的角色
    @Insert("insert into role_and_user " +
            "set uid=#{uid},rid=#{rid}")
    int setUserRoles(@Param("uid") Integer uid,@Param("rid") Integer rid);


}
