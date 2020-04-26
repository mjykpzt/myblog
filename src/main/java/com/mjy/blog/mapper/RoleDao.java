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
    @Select("select * from role where delete_flag=0")
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

    //通过角色id查询角色
    @Select("select * from role where id=#{rid}")
    Role findRoleByRid(Integer rid);

    //更新角色
    @Update("update role set role_name=#{name},role_des=#{des} where id=#{rid}")
    int updateRole(@Param("name")String name,@Param("des")String des,@Param("rid")Integer id);


}
