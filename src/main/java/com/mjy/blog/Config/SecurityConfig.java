package com.mjy.blog.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjy.blog.Bean.User;
import com.mjy.blog.Filter.AuthenticationAccessDeniedHandler;
import com.mjy.blog.Filter.TokenFilter;
import com.mjy.blog.Service.UserService;
import com.mjy.blog.Utils.TokenUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author mjy
 * @create 2020-03-23-22:15
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserService userService;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Resource
    private KeyConfig keyConfig;
    @Resource
    private AuthenticationAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**")
                .hasAnyRole("USER", "ADMIN", "TEST")
                .anyRequest().authenticated()
                .and()
                .addFilter(new TokenFilter(super.authenticationManager(), keyConfig))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().successHandler((httpServletRequest, httpServletResponse, authentication) -> {
            httpServletResponse.setContentType("application/json;charset=utf-8");


            String flushToken = TokenUtils.createToken(authentication, keyConfig, true, 60 * 24 * 3);
            httpServletResponse.addHeader("Set-Cookie", "flushToken=" + flushToken + ";HttpOnly; SameSite=Lax");
            User user = (User) authentication.getPrincipal();
            userService.updateUserLoginTime(user.getId());
            PrintWriter out = httpServletResponse.getWriter();
            HashMap<String, String> map = new HashMap<>();
            map.put("status", "1");
            map.put("msg", "登录成功");
            map.put("username", user.getUsername());
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
            out.close();
        }).failureHandler((httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            PrintWriter out = httpServletResponse.getWriter();
            out.write("{\"status\":\"0\",\"msg\":\"登录失败\"}");
            out.flush();
            out.close();
        })

                .and()
                .csrf().disable().exceptionHandling().accessDeniedHandler(accessDeniedHandler);

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/imgsave/**",
                "/RoleWebSocket/**", "/mass", "/UserWebSocket/**", "/ItemWebSocket/**", "/druid/**", "/getToken/**");

    }

}




