package com.mjy.blog.mapper;

import com.mjy.blog.Bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author mjy
 * @create 2020-03-07-18:31
 */
public interface UserDao {

    //通过id查找用户
    @Select("select * from user where id=#{uid} and delete_flag=0")
    @ResultMap("t1")
    User findById(Integer uid);

    //通过用户名查找用户
    @Select("<script>" + "select * from user where delete_flag=0 " +
            "<if test='searchName!=null'> " +
            "and username like #{searchName} " +
            "</if> " +
            "</script>")
    @Results(id = "t1", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "id", property = "roles", javaType = java.util.List.class,
                    many = @Many(select = "com.mjy.blog.mapper.RoleDao.findRoleNameByUid")
            )
    })
    List<User> findUserByName(@Param("searchName") String username);

    //添加用户
    @Insert("insert into user " +
            "set username=#{username},password=#{password},email=#{email},create_time=now()")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addUser(User user);

    //添加用户的角色
    @Insert("<script>" + "insert into role_and_user(uid,rid) " +
            "values <foreach collection='rids' item='rid'  separator=',' >(#{uid},#{rid})" +
            "</foreach></script>")
    int setUserRoles(@Param("uid") Integer uid, @Param("rids") Integer[] rids);


    //改变用户账号状态
    @Update("update user set status =#{status} where id=#{uid}")
    int changeUserStatus(@Param("status") Integer status, @Param("uid") Integer uid);

    //删除用户
    @Update("update user set delete_flag=1 where id=#{uid}")
    int delUser(Integer id);

    //修改用户
    @Update("update user set password=#{password},email=#{email} where id=#{uid}")
    int updateUser(@Param("password") String password, @Param("email") String email, @Param("uid") Integer uid);

    //解绑用户与角色
    @Delete("delete from role_and_user where uid=#{uid}")
    int delRolesFromUser(Integer uid);

    //查找是否存在该用户名
    @Select("select COUNT(*) from user where username=#{name} and #{random}=#{random}")
    int findIsHasName(String name,double random);
}
