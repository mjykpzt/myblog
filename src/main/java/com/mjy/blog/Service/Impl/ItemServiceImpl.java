package com.mjy.blog.Service.Impl;

import com.mjy.blog.Bean.Item;
import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.SysItem;
import com.mjy.blog.Service.ItemService;
import com.mjy.blog.mapper.ItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mjy
 * @create 2020-03-08-16:13
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao itemDao;
    @Override
    public ResponseBean findAll() {
        List<SysItem> allItem = itemDao.findItem(null);
        if (allItem !=null){
            return ResponseBean.getSuccessResponse("查询成功",allItem);
        }
        return ResponseBean.getFailResponse("查询失败");
    }

    @Override
    public ResponseBean findByUid(Integer uid) {
        List<SysItem> allItem = itemDao.findItem(uid);
        if (allItem !=null){
            return ResponseBean.getSuccessResponse("查询成功",allItem);
        }
        return ResponseBean.getFailResponse("查询失败");
    }

    @Override
    public ResponseBean addItem(String name, String des, Integer uid) {
        int i = itemDao.addItem(name, des, uid);
        if (i>0){
            return ResponseBean.getSuccessResponse("添加成功");
        }
        return ResponseBean.getFailResponse("添加失败");
    }
}
