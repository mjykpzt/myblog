package com.mjy.blog.Service;

import com.mjy.blog.Bean.ResponseBean;

/**
 * @author mjy
 * @create 2020-03-08-16:13
 */
public interface ItemService {
    ResponseBean findAll();

    ResponseBean findByUid(Integer uid);

    ResponseBean addItem(String name,String des,Integer uid);
}
