package com.mjy.blog.Bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author mjy
 * @create 2020-03-07-18:27
 */
@Data
public class User implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private Boolean status;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private Date create_time;
    private List<Role> roles;



}
