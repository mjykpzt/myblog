package com.mjy.blog.controller;

import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author mjy
 * @create 2020-03-08-16:21
 */
@RestController
@RequestMapping("/items")
public class ItemCon {
    @Autowired
    private ItemService itemService;

    @RequestMapping()
    public ResponseBean findAll(){
        return itemService.findAll();
    }

    @RequestMapping("/{uid}")
    public ResponseBean findByUid(@PathVariable Integer uid){
        return itemService.findByUid(uid);
    }

    @PostMapping("/addItem/add")
    public ResponseBean addItem(@RequestParam(required = true) String name, @RequestParam(required = true)String des){
        return itemService.addItem(name,des,1);
    }

    @PostMapping("/changeStatus")
    public ResponseBean changeStatus(@RequestParam(required = true)Short status,
                                     @RequestParam(required = true)Integer id){
        return itemService.changeStatus(status,id);
    }

    @PostMapping("/changeItem")
    public ResponseBean changeItem(@RequestParam(required = true)String name, @RequestParam(required = true)String des,
                                   @RequestParam(required = true)Integer id){
        return itemService.changeItem(name,des,id);
    }

}
