package com.mjy.blog.Service.Impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public ResponseBean findSimpleItems() {
        List<Item> simpleItems = itemDao.findSimpleItems();
        return ResponseBean.getSuccessResponse("查询成功",simpleItems);
    }

    @Override
    public ResponseBean findByUid(Integer uid, String searchName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysItem> allItem = itemDao.findItem(uid, searchName);
        PageInfo<SysItem> sysItemPageInfo = new PageInfo<>(allItem);

        return ResponseBean.getSuccessResponse("查询成功", sysItemPageInfo);

    }

    @Override
    public ResponseBean addItem(String name, String des, Integer uid) {
        int isHas = itemDao.findIsHasName(name);
        if (isHas == 0) {
            synchronized (this) {
                int isHasName = itemDao.findIsHasName(name);
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
        int isHas = itemDao.findIsHasName(name);
        switch (isHas) {
            case 0:
                synchronized (this) {
                    int isHasName = itemDao.findIsHasName(name);
                    if (isHasName == 0) {
                        return updateItem(name, des, id);
                    } else {
                        return ResponseBean.getFailResponse("名称重复");
                    }
                }
            case 1:
                synchronized (this) {
                    int isHasName = itemDao.findIsHasName(name);
                    switch (isHasName) {
                        case 0:
                            updateItem(name, des, id);
                        case 1:
                            int nameId = itemDao.findIdByName(name);
                            if (nameId == id) {
                                return updateItem(name, des, id);
                            } else {
                                return ResponseBean.getFailResponse("名称重复");
                            }
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
        int isHas = itemDao.findIsHasName(name);
        if (isHas > 1) {
            return ResponseBean.getFailResponse("名称重复");
        }
        return ResponseBean.getSuccessResponse("ok");
    }

    private ResponseBean updateItem(String name, String des, Integer id) {
        int i = itemDao.changeItem(name, des, id);
        if (i > 0) {
            return ResponseBean.getSuccessResponse("添加成功");
        }
        return ResponseBean.getFailResponse("添加失败");
    }

    @Override
    public ResponseBean delItem(Integer iid) {
        int articleNumber = itemDao.findArticleNumber(iid);
        if (articleNumber != 0) {
            return ResponseBean.getFailResponse("删除失败，请删除条目下文章再试");
        } else {
            int i = itemDao.delItem(iid);
            if (i > 0) {
                return ResponseBean.getSuccessResponse("删除成功");
            } else {
                return ResponseBean.getFailResponse("删除失败");
            }
        }
    }
}
