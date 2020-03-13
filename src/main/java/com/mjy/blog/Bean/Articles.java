package com.mjy.blog.Bean;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private Date create_time;
    private Integer create_user;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private Date change_time;
}
