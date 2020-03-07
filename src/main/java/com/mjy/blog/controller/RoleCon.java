package com.mjy.blog.controller;

import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mjy
 * @create 2020-03-08-0:26
 */
@RequestMapping("/role")
@RestController
public class RoleCon {
    @Autowired
    private RoleService roleService;
    @RequestMapping("/addrole")
    public ResponseBean addRole(String name,String desc){
        return roleService.addRole(name,desc);
    }
}
