package com.mjy.blog.controller;

import com.mjy.blog.bean.ResponseBean;
import com.mjy.blog.service.RoleService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author mjy
 * @create 2020-03-08-0:26
 */
@RequestMapping("/role")
@RestController
@Secured("ROLE_ADMIN")
public class RoleCon {
    @Resource
    private RoleService roleService;


    @PostMapping(value = "/addRole")
    public ResponseBean addRole(@RequestParam()String name, @RequestParam()String desc) {
        return roleService.addRole(name, desc);
    }


    @RequestMapping()
    public ResponseBean findAll() {
        return roleService.findAll();
    }


    @RequestMapping("/{uid}")
    public  ResponseBean findById(@PathVariable Integer uid){
        return  roleService.findById(uid);
    }


    @GetMapping("/findRoleById")
    public  ResponseBean findRoleById(@RequestParam() Integer rid){
        return roleService.findByRid(rid);
    }


    @PostMapping("/updateRole")
    public  ResponseBean updateRole(@RequestParam()String role_name,
                                    @RequestParam()String role_des,
                                    @RequestParam()Integer rid){
        return roleService.updateRole(role_name, role_des, rid);
    }
}
