package com.mjy.blog.Bean;

import lombok.Data;

import java.util.Date;

/**
 * @author mjy
 * @create 2020-03-08-21:20
 *
 */

@Data
public class Articles {
    private Integer id;
    private Integer item_id;
    private String title_name;
    private String text_re;
    private String text;
    private Short status;
    private Date create_time;
    private Integer create_user;
    private Date change_time;
}
