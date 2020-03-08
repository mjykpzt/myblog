package com.mjy.blog.Bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author mjy
 * @create 2020-03-07-18:27
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private Integer status;
    private Date create_time;
    private List<Role> roles;

}
