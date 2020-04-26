package com.mjy.blog.Bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mjy
 * @create 2020-03-08-21:20
 *
 */

@Data
public class Articles implements Serializable {
    private Integer id;
    private Integer item_id;
    private String title_name;
    private String md_text;
    private String source_text;
    private String html_text;
    private Short status;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private Date create_time;
    private Integer create_user;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private Date change_time;
    private Integer read_numbers;
}
