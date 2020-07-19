package com.mjy.blog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mjy.blog.bean.ResponseBean;
import com.mjy.blog.bean.TokenEnum;
import com.mjy.blog.bean.User;
import com.mjy.blog.mapper.UserDao;
import com.mjy.blog.service.RedisService;
import com.mjy.blog.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mjy
 * @create 2020-03-07-18:43
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Resource
    private RedisService redisService;


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor =Exception.class)
    public ResponseBean addUser(User user) {
        int isHas = userDao.findIsHasName(user.getUsername(), Math.random());
        if (isHas == 0) {
            synchronized (this) {
                int isHasName = userDao.findIsHasName(user.getUsername(), Math.random());
                if (isHasName == 0) {
                    String password = user.getPassword();
                    user.setPassword(bCryptPasswordEncoder.encode(password));
                    int i = userDao.addUser(user);
                    int id = user.getId();
                    Integer[] ids = {1};
                    int i1 = userDao.setUserRoles(id, ids);
                    if (i > 0 && i1 > 0) {
                        return ResponseBean.getSuccessResponse("添加成功");
                    }
                    return ResponseBean.getFailResponse("添加失败");
                } else {
                    return ResponseBean.getFailResponse("名称重复");
                }
            }
        }
        return ResponseBean.getFailResponse("名称重复");
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseBean findByName(String searchName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userDao.findUserByName(searchName);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return ResponseBean.getSuccessResponse("查询成功", pageInfo);


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
    public ResponseBean delUser(Integer uid) {
        int statusCode = userDao.delUser(uid);
        if (statusCode > 0) {
            return ResponseBean.getSuccessResponse("删除成功");
        }
        return ResponseBean.getFailResponse("删除失败");
    }

    @Override
    public ResponseBean updateUserInformation(String email, Integer uid) {
        int statusCode = userDao.updateUserInformation(email, uid);
        if (statusCode > 0) {
            return ResponseBean.getSuccessResponse("修改成功");
        }
        return ResponseBean.getFailResponse("修改失败");
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor =Exception.class)
    @Override
    public ResponseBean updateUserPassword(String password, Integer uid) {
        String encodePassword = bCryptPasswordEncoder.encode(password);
        int statusCode = userDao.updateUserPassword(encodePassword, uid);
        if (statusCode > 0) {
            if (redisService.WhiteToBlackTokens(uid, TokenEnum.FLUSH_TOKEN_HEADER, TokenEnum.AUTHORIZATION_TOKEN_HEADER)) {
                return ResponseBean.getSuccessResponse("修改成功");
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        }
        return ResponseBean.getFailResponse("修改失败");
    }


    @Transactional(readOnly = true)
    @Override
    public ResponseBean findById(Integer id) {
        User user = userDao.findById(id);
        if (user != null) {
            return ResponseBean.getSuccessResponse("查询成功", user);
        }

        return ResponseBean.getFailResponse("查询失败或未查询到数据");
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor =Exception.class)
    @Override
    public ResponseBean updateRoles(Integer uid, Integer[] roles) {
        int s1 = userDao.delRolesFromUser(uid);
        int s2 = userDao.setUserRoles(uid, roles);
        if (s1 > 0 && s2 > 0) {
            return ResponseBean.getSuccessResponse("权限修改成功");
        }

        return ResponseBean.getFailResponse("权限修改失败");
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findUserByNameLoad(username);
    }


    @Override
    public ResponseBean findIsHasName(String name) {
        int isHas = userDao.findIsHasName(name, Math.random());
        if (isHas > 0) {
            return ResponseBean.getFailResponse("名称重复");
        }
        return ResponseBean.getSuccessResponse("ok");
    }

    @Override
    public void updateUserLoginTime(Integer uid) {
        int i = userDao.updateUserLoginTime(uid);
    }
}
