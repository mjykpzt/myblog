package com.mjy.blog.bean;



/**
 * @author mjy
 * @create 2020-03-08-16:25
 */

public class SysItem extends Item {
    private String create_name;

    public String getCreate_name() {
        return create_name;
    }

    public void setCreate_name(String create_name) {
        this.create_name = create_name;
    }

    @Override
    public String toString() {
        return super.toString()+"SysItem{" +
                "create_name='" + create_name + '\'' +
                '}';
    }
}
