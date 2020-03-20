package com.mjy.blog.Bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mjy
 * @create 2020-03-07-22:01
 */
@Data
public class Role implements Serializable {
    private Integer id;
    private String role_name;
    private String role_des;
}
