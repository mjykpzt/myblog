package com.mjy.blog.controller;

import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.User;
import com.mjy.blog.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author mjy
 * @create 2020-03-07-18:33
 */
@RestController
@RequestMapping("/users")
@Secured("ROLE_ADMIN")
public class UserCon {
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseBean findUsers(String searchName,
                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "5") Integer pageSize) {
        if (!(searchName.length() > 0)) {
            searchName = null;
        } else {
            searchName = "%" + searchName + "%";
        }
        return userService.findByName(searchName, pageNum, pageSize);
    }


    @PostMapping(value = "/addUser")
    public ResponseBean addUser(@Validated User user) {
        return userService.addUser(user);
    }

    @PostMapping("/changeUserStatus")
    public ResponseBean changeUserStatus(@RequestParam() Integer userid,
                                         @RequestParam() Integer userstatus) {
        return userService.changeUserStatus(userstatus, userid);
    }


    @PostMapping("/delUser")
    public ResponseBean delUser(@RequestParam() Integer uid) {
        return userService.delUser(uid);
    }

    @PostMapping("/updateUser")
    public ResponseBean updateUser(@RequestParam() String password,
                                   @RequestParam() String email,
                                   @RequestParam() Integer uid
    ) {
        return userService.updateUser(password, email, uid);
    }

    @GetMapping("/findById")
    public ResponseBean findById(@RequestParam() Integer uid) {
        return userService.findById(uid);
    }

    @RequestMapping("/updateRoles")
    public ResponseBean updateRoles(@RequestParam() Integer[] roles, @RequestParam(required = true) Integer id) {
        return userService.updateRoles(id, roles);

    }

    @GetMapping("/findIsHasName")
    public ResponseBean findIsHasName(@RequestParam() String username) {
        return userService.findIsHasName(username);
    }
}
