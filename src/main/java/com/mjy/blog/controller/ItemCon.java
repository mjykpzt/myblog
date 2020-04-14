package com.mjy.blog.controller;

import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
    public ResponseBean findByUid(HttpServletRequest request, String searchName,
                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "5") Integer pageSize) {
        if (!(searchName.length() > 0)) {
            searchName = null;
        }else {
            searchName="%"+searchName+"%";
        }
        return itemService.findByUid((Integer) request.getAttribute("uid"),searchName, pageNum, pageSize);
    }

    @PostMapping("/addItem/add")
    @Secured("ROLE_ADMIN")
    public ResponseBean addItem(HttpServletRequest request, @RequestParam(required = true) String item_name, @RequestParam(required = true) String item_des) {
        return itemService.addItem(item_name, item_des, (Integer) request.getAttribute("uid"));
    }

    @PostMapping("/changeStatus")
    @Secured("ROLE_ADMIN")
    public ResponseBean changeStatus(@RequestParam(required = true) Short status,
                                     @RequestParam(required = true) Integer id) {
        return itemService.changeStatus(status, id);
    }

    @PostMapping("/changeItem")
    @Secured("ROLE_ADMIN")
    public ResponseBean changeItem(@RequestParam(required = true) String item_name, @RequestParam(required = true) String item_des,
                                   @RequestParam(required = true) Integer id) {
        return itemService.changeItem(item_name, item_des, id);
    }

    @GetMapping("/findByIid/{iid}")
    public ResponseBean findByIid(@PathVariable Integer iid) {
        return itemService.findItemByIid(iid);
    }


    @GetMapping("/item/isHas")
    public ResponseBean ishas(String name) {
        return itemService.findIsHasName(name);
    }

    @PostMapping("/delItem")
    @Secured("ROLE_ADMIN")
    public ResponseBean delItem(Integer iid) {
        return itemService.delItem(iid);
    }
}
