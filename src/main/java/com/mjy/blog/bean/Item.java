package com.mjy.blog.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mjy
 * @create 2020-03-08-15:59
 */

public class Item implements Serializable {
    private Integer id;
    private String item_name;
    private String item_des;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private Date create_time;
    private Integer create_user;
    private boolean status;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    private Date change_time;
    private Integer articles_number;
    private Integer articles_number_user;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setItem_des(String item_des) {
        this.item_des = item_des;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public void setCreate_user(Integer create_user) {
        this.create_user = create_user;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setChange_time(Date change_time) {
        this.change_time = change_time;
    }

    public void setArticles_number(Integer articles_number) {
        this.articles_number = articles_number;
    }

    public void setArticles_number_user(Integer articles_number_user) {
        this.articles_number_user = articles_number_user;
    }

    public Integer getId() {
        return id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_des() {
        return item_des;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public Integer getCreate_user() {
        return create_user;
    }

    public boolean isStatus() {
        return status;
    }

    public Date getChange_time() {
        return change_time;
    }

    public Integer getArticles_number() {
        return articles_number;
    }

    public Integer getArticles_number_user() {
        return articles_number_user;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", item_name='" + item_name + '\'' +
                ", item_des='" + item_des + '\'' +
                ", create_time=" + create_time +
                ", create_user=" + create_user +
                ", status=" + status +
                ", change_time=" + change_time +
                ", articles_number=" + articles_number +
                ", articles_number_user=" + articles_number_user +
                '}';
    }
}
