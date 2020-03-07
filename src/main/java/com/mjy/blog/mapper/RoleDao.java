package com.mjy.blog.mapper;

import com.mjy.blog.Bean.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author mjy
 * @create 2020-03-07-22:03
 */
public interface RoleDao {
    //查询所有角色
    @Select("select * from role")
    List<Role> findAllRoles();

    //根据用户ID查询该用户的角色
    @Select("select r.id,r.role_name,r.role_des " +
            "from role_and_user ru,role r " +
            "where ru.uid=#{uid} and r.id=ru.rid")
    @ResultType(Role.class)
    List<Role> findRoleNameByUid(Integer uid);

    //添加角色
    @Insert("insert into role " +
            "set role_name=#{name},role_des=#{decs}")
    int addRole(@Param("name")String name,@Param("decs")String decs);
}
