package com.mjy.blog.mapper;

import com.mjy.blog.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author mjy
 * @create 2020-03-07-18:31
 */
public interface UserDao {

    //通过id查找用户
    @Select("select id,username,email,status,create_time,last_login_time from user where id=#{uid} and delete_flag=0")
    @ResultMap("findRole")
    User findById(Integer uid);

    //通过用户名查找用户
    @Select("<script>" + "select id,username,email,status,create_time,last_login_time from user where delete_flag=0 " +
            "<if test='searchName!=null'> " +
            "and username like #{searchName} " +
            "</if> " +
            "</script>")
    @Results(id = "findRole", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "id", property = "roles", javaType = java.util.List.class,
                    many = @Many(select = "com.mjy.blog.mapper.RoleDao.findRoleNameByUid")
            )
    })
    List<User> findUserByName(@Param("searchName") String username);

    //用户名查找
    @Select("select id,username,password,status from user where delete_flag=0 and username=#{username}")
    @ResultMap("findRole")
    User findUserByNameLoad(@Param("username") String username);

    //添加用户
    @Insert("insert into user " +
            "set username=#{username},password=#{password},email=#{email},create_time=now()")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addUser(User user);

    //添加用户的角色
    @Insert("<script>" +
            "insert into role_and_user(uid,rid) " +
            "values " +
            "<foreach collection='rids' item='rid'  separator=',' >(#{uid},#{rid})" +
            "</foreach></script>")
    int setUserRoles(@Param("uid") Integer uid, @Param("rids") Integer[] rids);


    //改变用户账号状态
    @Update("update user set status =#{status} where id=#{uid}")
    int changeUserStatus(@Param("status") Integer status, @Param("uid") Integer uid);

    //删除用户
    @Update("update user set delete_flag=1 where id=#{uid}")
    int delUser(Integer id);

    /**
     * 修改用户信息
     *
     * @param email  用户邮箱
     * @param uid    用户ID
     * @return: int
     * @author: 0205
     */
    @Update("update user set email=#{email} where id=#{uid}")
    int updateUserInformation(@Param("email") String email, @Param("uid") Integer uid);


    @Update("update user set password =#{password} where id = #{uid}")
    int updateUserPassword(@Param("password")String password,@Param("uid") Integer uid);


    //解绑用户与角色
    @Delete("delete from role_and_user where uid=#{uid}")
    int delRolesFromUser(Integer uid);

    //查找是否存在该用户名
    @Select("select COUNT(*) from user where username=#{name} and #{random}=#{random}")
    int findIsHasName(String name,double random);

    /**
     * @param uid 用户id
     * @return: int
     * @author: 0205
     *
     * 更改用户登录时间
     */
    @Update("update `user` set last_login_time=now() where id=#{uid}")
    int updateUserLoginTime(Integer uid);
}
