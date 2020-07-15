package com.mjy.blog.service;

import com.mjy.blog.bean.ResponseBean;

/**
 * @author mjy
 * @create 2020-03-08-16:13
 */
public interface ItemService {
    ResponseBean findSimpleItems();

    ResponseBean addItem(String name,String des,Integer uid);

    ResponseBean changeStatus(Short status,Integer id);

    ResponseBean changeItem(String name,String des,Integer id);

    ResponseBean findItemByIid(Integer iid);

    ResponseBean findIsHasName(String name);

    ResponseBean delItem(Integer iid);

    ResponseBean findByUid(Integer uid, String searchName, Integer pageNum, Integer pageSize);
}
