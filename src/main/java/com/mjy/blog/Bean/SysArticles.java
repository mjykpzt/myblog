package com.mjy.blog.Bean;

import lombok.Data;

/**
 * @author mjy
 * @create 2020-03-08-21:22
 *
 *
 * 该类将创造者的id和该文章所属条目的id转换为创造者名称和条目名称
 */
@Data
public class SysArticles extends Articles {
    private String create_name;
    private String item_name;
}
