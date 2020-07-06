package com.mjy.blog.Bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author mjy
 * @create 2020-03-08-21:20
 */


@Validated
public class Articles implements Serializable {
    private Integer id;
    private Integer item_id;
    @Size(min = 1, max = 32, message = "文章标题长度为1-32个字符")
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

    public Integer getId() {
        return id;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public String getTitle_name() {
        return title_name;
    }

    public String getMd_text() {
        return md_text;
    }

    public String getSource_text() {
        return source_text;
    }

    public String getHtml_text() {
        return html_text;
    }

    public Short getStatus() {
        return status;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public Integer getCreate_user() {
        return create_user;
    }

    public Date getChange_time() {
        return change_time;
    }

    public Integer getRead_numbers() {
        return read_numbers;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public void setTitle_name(String title_name) {
        this.title_name = title_name;
    }

    public void setMd_text(String md_text) {
        this.md_text = md_text;
    }

    public void setSource_text(String source_text) {
        this.source_text = source_text;
    }

    public void setHtml_text(String html_text) {
        this.html_text = html_text;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public void setCreate_user(Integer create_user) {
        this.create_user = create_user;
    }

    public void setChange_time(Date change_time) {
        this.change_time = change_time;
    }

    public void setRead_numbers(Integer read_numbers) {
        this.read_numbers = read_numbers;
    }

    @Override
    public String toString() {
        return "Articles{" +
                "id=" + id +
                ", item_id=" + item_id +
                ", title_name='" + title_name + '\'' +
                ", md_text='" + md_text + '\'' +
                ", source_text='" + source_text + '\'' +
                ", html_text='" + html_text + '\'' +
                ", status=" + status +
                ", create_time=" + create_time +
                ", create_user=" + create_user +
                ", change_time=" + change_time +
                ", read_numbers=" + read_numbers +
                '}';
    }

}
