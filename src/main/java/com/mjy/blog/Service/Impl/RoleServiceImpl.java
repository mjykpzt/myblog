package com.mjy.blog.Service.Impl;


import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.Role;
import com.mjy.blog.Service.RoleService;
import com.mjy.blog.mapper.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


/**
 * @author mjy
 * @create 2020-03-08-0:29
 */
@Service
//@Transactional(isolation = Isolation.READ_UNCOMMITTED)
//@Transactional()
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;


    @Override
    public ResponseBean addRole(String name, String desc) {
        String roleName = "ROLE_" + name;
        int i = roleDao.addRole(roleName, desc);
        if (i > 0) {
            return ResponseBean.getSuccessResponse("添加成功");
        }
        return ResponseBean.getFailResponse("添加失败");
    }

    @Override
//    @Transactional(readOnly = true)
    public ResponseBean findAll() {
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        List<Role> allRoles = roleDao.findAllRoles();
        if (allRoles != null && allRoles.size() > 0) {
            System.out.println(allRoles);
            return ResponseBean.getSuccessResponse("查询成功", allRoles);
        }
        return ResponseBean.getFailResponse("查询失败或未查询到数据");
    }

    @Override
    public ResponseBean findById(Integer uid) {
        List<Role> allRoles = roleDao.findRoleNameByUid(uid);
        if (allRoles != null && allRoles.size() > 0) {

            return ResponseBean.getSuccessResponse("查询成功", allRoles);
        }
        return ResponseBean.getFailResponse("查询失败或未查询到数据");
    }

    @Override
    public ResponseBean findByRid(Integer rid) {
        Role role = roleDao.findRoleByRid(rid);
        if (role != null) {
            return ResponseBean.getSuccessResponse("查询成功", role);
        }
        return ResponseBean.getFailResponse("查询失败或未查询到数据");
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseBean updateRole(String name, String des, Integer rid) {
        int i = roleDao.updateRole(name, des, rid);
        if (i > 0) {
//            try {
//                ProductWebSocket.sendInfo("flush");//给前端发送指令，告诉前端数据库有变化，让前端重新请求数据
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            return ResponseBean.getSuccessResponse("更新成功");
        }
        return ResponseBean.getFailResponse("更新失败");
    }
}
