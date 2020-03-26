package com.mjy.blog.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author mjy
 * @create 2020-03-07-22:01
 */
@Data
public class Role implements GrantedAuthority {
    private Integer id;
    private String role_name;
    private String role_des;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return role_name;
    }
}
