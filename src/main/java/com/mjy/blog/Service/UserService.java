package com.mjy.blog.Service;

import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.User;

/**
 * @author mjy
 * @create 2020-03-07-18:42
 */
public interface UserService {
    ResponseBean findAll();

    ResponseBean findByName(String username);

    ResponseBean addUser(User user);

    ResponseBean changeUserStatus(Integer status,Integer uid);

    ResponseBean searchUser(String username);
}
//123
