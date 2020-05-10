package com.mjy.blog.Bean;

import lombok.Data;

import java.util.List;

/**
 * @author mjy
 * @create 2020-05-08-23:46
 */
@Data
public class Test {
    private Integer id;
    private String name;
    private List<Test> clist;
}
