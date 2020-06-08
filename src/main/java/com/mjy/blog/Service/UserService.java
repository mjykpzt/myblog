package com.mjy.blog.Service;

import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author mjy
 * @create 2020-03-07-18:42
 */
public interface UserService extends UserDetailsService {
//    ResponseBean findAll(Integer pageNum,Integer pageSize);

    ResponseBean findByName(String searchName,Integer pageNum,Integer pageSize);

    ResponseBean addUser(User user);

    ResponseBean changeUserStatus(Integer status,Integer uid);

//    ResponseBean searchUser(String username,Integer pageNum,Integer pageSize);

    ResponseBean delUser(Integer uid);

    ResponseBean updateUser(String password,String email,Integer uid);

    ResponseBean findById(Integer id);

    ResponseBean updateRoles(Integer uid,Integer[] roles);

    ResponseBean findIsHasName(String name);

    Boolean updateUserLoginTime(Integer uid);
}

