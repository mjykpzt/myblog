package com.mjy.blog.Bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mjy
 * @create 2020-03-08-15:59
 */
@Data
public class Item implements Serializable {
    private Integer id;
    private String item_name;
    private String item_des;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private Date create_time;
    private Integer create_user;
    private Short status;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private Date change_time;
}
