package com.mjy.blog.service;

import com.mjy.blog.bean.ResponseBean;

/**
 * @author mjy
 * @create 2020-03-08-0:29
 */
public interface RoleService {
    ResponseBean addRole(String name,String desc);

    ResponseBean findAll();

    ResponseBean findById(Integer uid);

    ResponseBean findByRid(Integer rid);

    ResponseBean updateRole(String name,String des,Integer rid);
}
