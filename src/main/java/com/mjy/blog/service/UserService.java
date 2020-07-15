package com.mjy.blog.service;

import com.mjy.blog.bean.ResponseBean;
import com.mjy.blog.bean.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author mjy
 * @create 2020-03-07-18:42
 */
public interface UserService extends UserDetailsService {

    ResponseBean findByName(String searchName,Integer pageNum,Integer pageSize);

    ResponseBean addUser(User user);

    ResponseBean changeUserStatus(Integer status,Integer uid);

    ResponseBean delUser(Integer uid);

    ResponseBean updateUserInformation(String email,Integer uid);

    ResponseBean updateUserPassword(String password,Integer uid);

    ResponseBean findById(Integer id);

    ResponseBean updateRoles(Integer uid,Integer[] roles);

    ResponseBean findIsHasName(String name);

    void updateUserLoginTime(Integer uid);
}

