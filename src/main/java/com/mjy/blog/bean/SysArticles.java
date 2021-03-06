package com.mjy.blog.bean;



/**
 * @author mjy
 * @create 2020-03-08-21:22
 *
 *
 * 该类将文章作者的id和该文章所属条目的id转换为作者名称和条目名称
 */

public class SysArticles extends Articles {
    private String create_name;
    private String item_name;

    public String getCreate_name() {
        return create_name;
    }

    public void setCreate_name(String create_name) {
        this.create_name = create_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    @Override
    public String toString() {
        return super.toString()+"SysArticles{" +
                "create_name='" + create_name + '\'' +
                ", item_name='" + item_name + '\'' +
                '}';
    }
}
