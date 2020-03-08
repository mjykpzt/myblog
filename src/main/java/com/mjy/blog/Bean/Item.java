package com.mjy.blog.Bean;

import lombok.Data;

import java.util.Date;

/**
 * @author mjy
 * @create 2020-03-08-15:59
 */
@Data
public class Item {
    private Integer id;
    private String item_name;
    private String item_des;
    private Date creat_time;
    private Integer creat_user;
    private Short status;
    private Date change_time;
}
