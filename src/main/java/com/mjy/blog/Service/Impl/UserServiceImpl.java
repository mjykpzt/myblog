package com.mjy.blog.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.User;
import com.mjy.blog.Service.UserService;
import com.mjy.blog.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mjy
 * @create 2020-03-07-18:43
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public ResponseBean findAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userDao.findAll();
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        if (pageInfo != null) {
            return ResponseBean.getSuccessResponse("查询成功", pageInfo);
        }

        return ResponseBean.getFailResponse("查询失败或未查询到数据");
    }

    @Override
    public ResponseBean addUser(User user) {
        int i = userDao.addUser(user);
        int id = user.getId();
        Integer[] ids = {1};
        int i1 = userDao.setUserRoles(id, ids);
        if (i > 0 && i1 > 0) {
            return ResponseBean.getSuccessResponse("添加成功");
        }
        return ResponseBean.getFailResponse("添加失败");
    }

    @Override
    public ResponseBean findByName(String username) {
        List<User> userList = userDao.findUserByName(username, false);
        if (userList != null && userList.size() != 0) {
            return ResponseBean.getSuccessResponse("查询成功", userList);
        }

        return ResponseBean.getFailResponse("查询失败或未查询到数据");
    }

    @Override
    public ResponseBean changeUserStatus(Integer status, Integer uid) {
        if (status != 1 && status != 0) {
            return ResponseBean.getFailResponse("状态码错误");
        }
        int i = userDao.changeUserStatus(status, uid);
        if (i > 0) {
            return ResponseBean.getSuccessResponse("改变用户账号状态成功");
        }
        return ResponseBean.getFailResponse("改变用户账号状态失败");
    }

    @Override
    public ResponseBean searchUser(String username, Integer pageNum, Integer pageSize) {
        String name = "%" + username + "%";
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userDao.findUserByName(name, true);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        if (pageInfo != null) {
            return ResponseBean.getSuccessResponse("查询成功", pageInfo);
        }

        return ResponseBean.getFailResponse("查询失败或未查询到数据");
    }

    @Override
    public ResponseBean delUser(Integer uid) {
        int statusCode = userDao.delUser(uid);
        if (statusCode > 0) {
            return ResponseBean.getSuccessResponse("删除成功");
        }
        return ResponseBean.getFailResponse("删除失败");
    }

    @Override
    public ResponseBean updateUser(String password, String email, Integer uid) {
        int statusCode = userDao.updateUser(password, email, uid);
        if (statusCode > 0) {
            return ResponseBean.getSuccessResponse("修改成功");
        }
        return ResponseBean.getFailResponse("修改失败");
    }

    @Override
    public ResponseBean findById(Integer id) {
        User user = userDao.findById(id);
        if (user != null) {
            return ResponseBean.getSuccessResponse("查询成功", user);
        }

        return ResponseBean.getFailResponse("查询失败或未查询到数据");
    }

    @Override
    public ResponseBean updateRoles(Integer uid, Integer[] roles) {
        int s1 = userDao.delRolesFromUser(uid);
        int s2 = userDao.setUserRoles(uid, roles);
        if (s1 > 0 && s2 > 0) {
            return ResponseBean.getSuccessResponse("权限修改成功");
        }

        return ResponseBean.getFailResponse("权限修改失败");
    }

}
