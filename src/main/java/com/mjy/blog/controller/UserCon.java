package com.mjy.blog.controller;

import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.User;
import com.mjy.blog.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
        }else {
            searchName="%"+searchName+"%";
        }
        return userService.findByName(searchName, pageNum, pageSize);
    }

//    @GetMapping("/searchname/{username}")
//    public ResponseBean findByName(@PathVariable String username) {
//        return userService.findByName(username);
//    }

    @PostMapping(value = "/addUser")
    public ResponseBean addUser(@RequestParam(required = true) String username,
                                @RequestParam(required = true) String password,
                                @RequestParam(required = true) String email
    ) {
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setEmail(email);
        return userService.addUser(user);
    }

    @PostMapping("/changeUserStatus")
    public ResponseBean changeUserStatus(@RequestParam(required = true) Integer userid,
                                         @RequestParam(required = true) Integer userstatus) {
        return userService.changeUserStatus(userstatus, userid);
    }

//    @PostMapping("/searchUser")
//    public ResponseBean searchUser(@RequestParam(required = true) String str, @RequestParam(defaultValue = "1")
//            Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
//        return userService.searchUser(str, pageNum, pageSize);
//    }

    @PostMapping("/delUser")
    public ResponseBean delUser(@RequestParam(required = true) Integer uid) {
        return userService.delUser(uid);
    }

    @PostMapping("/updateUser")
    public ResponseBean updateUser(@RequestParam(required = true) String password,
                                   @RequestParam(required = true) String email,
                                   @RequestParam(required = true) Integer uid
    ) {
        return userService.updateUser(password, email, uid);
    }

    @GetMapping("/findById")
    public ResponseBean findById(@RequestParam(required = true) Integer uid) {
        return userService.findById(uid);
    }

    @RequestMapping("/updateRoles")
    public ResponseBean updateRoles(@RequestParam(required = true) Integer[] roles, @RequestParam(required = true) Integer id) {
        return userService.updateRoles(id, roles);

    }

    @GetMapping("/findIsHasName")
    public ResponseBean findIsHasName(@RequestParam(required = true) String username) {
        return userService.findIsHasName(username);
    }
}
