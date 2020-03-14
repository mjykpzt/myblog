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
    public ResponseBean findAll(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList = userDao.findAll();
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        if (pageInfo != null){
            return ResponseBean.getSuccessResponse("查询成功",pageInfo);
        }

        return ResponseBean.getFailResponse("查询失败或未查询到数据");
    }

    @Override
    public ResponseBean addUser(User user) {
        int i = userDao.addUser(user);
        Integer id = user.getId();
        int i1 = userDao.setUserRoles(id, 1);
        if (i >0 && i1>0){
            return ResponseBean.getSuccessResponse("添加成功");
        }
        return ResponseBean.getFailResponse("添加失败");
    }

    @Override
    public ResponseBean findByName(String username) {
        List<User> userList = userDao.findUserByName(username, false);
        if (userList != null && userList.size()!=0){
            return ResponseBean.getSuccessResponse("查询成功",userList);
        }

        return ResponseBean.getFailResponse("查询失败或未查询到数据");
    }

    @Override
    public ResponseBean changeUserStatus(Integer status, Integer uid) {
        if (status!= 1 && status != 0){
            return ResponseBean.getFailResponse("状态码错误");
        }
        int i = userDao.changeUserStatus(status, uid);
        if (i>0){
            return ResponseBean.getSuccessResponse("改变用户账号状态成功");
        }
        return ResponseBean.getFailResponse("改变用户账号状态失败");
    }

    @Override
    public ResponseBean searchUser(String username) {
        String name = "%"+username+"%";
        List<User> userList = userDao.findUserByName(name, true);
        if (userList != null){
            return ResponseBean.getSuccessResponse("查询成功",userList);
        }

        return ResponseBean.getFailResponse("查询失败或未查询到数据");
    }
}
