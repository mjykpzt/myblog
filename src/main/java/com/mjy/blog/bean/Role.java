package com.mjy.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author mjy
 * @create 2020-03-07-22:01
 */

@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
public class Role implements GrantedAuthority {
    private Integer id;
    private String role_name;
    private String role_des;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return role_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getRole_des() {
        return role_des;
    }

    public void setRole_des(String role_des) {
        this.role_des = role_des;
    }
}
