package com.mjy.blog.Filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author mjy
 * @create 2020-03-24-14:04
 */
public class TokenFilter extends BasicAuthenticationFilter {

    public TokenFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }
}

