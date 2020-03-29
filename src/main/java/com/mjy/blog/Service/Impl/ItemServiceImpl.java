package com.mjy.blog.Service.Impl;


import com.mjy.blog.Bean.Item;
import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.SysItem;
import com.mjy.blog.Service.ItemService;
import com.mjy.blog.mapper.ItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mjy
 * @create 2020-03-08-16:13
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao itemDao;

    @Override
    public ResponseBean findAll() {
        List<SysItem> allItem = itemDao.findItem(null);
        if (allItem != null) {
            return ResponseBean.getSuccessResponse("查询成功", allItem);
        }
        return ResponseBean.getFailResponse("查询失败");
    }

    @Override
    public ResponseBean findByUid(Integer uid) {
        List<SysItem> allItem = itemDao.findItem(uid);
        if (allItem != null) {
            return ResponseBean.getSuccessResponse("查询成功", allItem);
        }
        return ResponseBean.getFailResponse("查询失败");
    }

    @Override
    public ResponseBean addItem(String name, String des, Integer uid) {
        int isHas = itemDao.findIsHasName(name,Math.random());//Math.random()生成随机数，避免使用一级缓存
        if (isHas == 0) {
            synchronized (this) {
                int isHasName = itemDao.findIsHasName(name,Math.random());
                if (isHasName == 0) {
                    int i = itemDao.addItem(name, des, uid);
                    if (i > 0) {
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

    @Override
    public ResponseBean changeStatus(Short status, Integer id) {
        int i = itemDao.changeItemStatus(status, id);
        if (i > 0) {
            return ResponseBean.getSuccessResponse("改变成功");
        }
        return ResponseBean.getFailResponse("改变失败");
    }

    @Override
    public ResponseBean changeItem(String name, String des, Integer id) {
        int isHas = itemDao.findIsHasName(name,Math.random());
        if (isHas == 0) {
            synchronized (this) {
                int isHasName = itemDao.findIsHasName(name,Math.random());
                if (isHasName == 0) {
                    int i = itemDao.changeItem(name, des, id);
                    if (i > 0) {
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

    @Override
    public ResponseBean findItemByIid(Integer iid) {
        Item itemByIid = itemDao.findItemByIid(iid);
        if (itemByIid != null) {
            return ResponseBean.getSuccessResponse("查询成功", itemByIid);
        }
        return ResponseBean.getFailResponse("查询失败");
    }

    @Override
    public ResponseBean findIsHasName(String name) {
        int isHas = itemDao.findIsHasName(name,Math.random());
        System.out.println("这是查询结果："+isHas);
        if (isHas > 0) {
            return ResponseBean.getFailResponse("名称重复");
        }
        return ResponseBean.getSuccessResponse("ok");
    }
}
