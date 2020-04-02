package com.mjy.blog.controller;

import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mjy
 * @create 2020-03-08-16:21
 */
@RestController
@RequestMapping("/items")
public class ItemCon {
    @Autowired
    private ItemService itemService;

    @GetMapping()
    public ResponseBean findAll(){
        return itemService.findAll();
    }

    @GetMapping("/{uid}")
    public ResponseBean findByUid(@PathVariable Integer uid){
        return itemService.findByUid(uid);
    }

    @PostMapping("/addItem/add")
    public ResponseBean addItem(HttpServletRequest request, @RequestParam(required = true) String item_name, @RequestParam(required = true)String item_des){
        return itemService.addItem(item_name,item_des,(Integer) request.getAttribute("uid"));
    }

    @PostMapping("/changeStatus")
    public ResponseBean changeStatus(@RequestParam(required = true)Short status,
                                     @RequestParam(required = true)Integer id){
        return itemService.changeStatus(status,id);
    }

    @PostMapping("/changeItem")
    public ResponseBean changeItem(@RequestParam(required = true)String item_name, @RequestParam(required = true)String item_des,
                                   @RequestParam(required = true)Integer id){
        return itemService.changeItem(item_name, item_des, id);
    }

    @GetMapping("/findByIid/{iid}")
    public ResponseBean findByIid(@PathVariable Integer iid){
        return itemService.findItemByIid(iid);
    }


    @GetMapping("/item/isHas")
    public  ResponseBean ishas(String name){return itemService.findIsHasName(name);}

    @PostMapping("/delItem")
    public  ResponseBean delItem(Integer iid){return itemService.delItem(iid);}
}
