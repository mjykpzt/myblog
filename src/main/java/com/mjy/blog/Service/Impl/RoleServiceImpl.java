package com.mjy.blog.Service.Impl;

import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Service.RoleService;
import com.mjy.blog.mapper.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mjy
 * @create 2020-03-08-0:29
 */
@Service
public class RoleServiceImpl  implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public ResponseBean addRole(String name, String desc) {
        String roleName = "ROLE_"+name;
        int i = roleDao.addRole(roleName, desc);
        if (i>0){
            return ResponseBean.getSuccessResponse("添加成功");
        }
        return ResponseBean.getFailResponse("添加失败");
    }
}
