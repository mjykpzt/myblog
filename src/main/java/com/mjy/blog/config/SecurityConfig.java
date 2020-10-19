package com.mjy.blog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjy.blog.bean.TokenEnum;
import com.mjy.blog.bean.User;
import com.mjy.blog.filter.AuthenticationAccessDeniedHandler;
import com.mjy.blog.filter.TokenFilter;
import com.mjy.blog.service.RedisService;
import com.mjy.blog.service.UserService;
import com.mjy.blog.utils.TokenUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
    @Resource
    private RedisService redisService;

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
                .addFilter(new TokenFilter(super.authenticationManager(), keyConfig, redisService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().successHandler((httpServletRequest, httpServletResponse, authentication) -> {
            httpServletResponse.setContentType("application/json;charset=utf-8");

            User user = (User) authentication.getPrincipal();
            String flushToken = TokenUtils.createToken(authentication, keyConfig, true, 60 * 24);
            redisService.WhiteToBlackTokens(user.getId(), TokenEnum.FLUSH_TOKEN_HEADER, TokenEnum.AUTHORIZATION_TOKEN_HEADER);
            httpServletResponse.addHeader("Set-Cookie", TokenEnum.FLUSH_TOKEN_HEADER +"=" + flushToken + ";HttpOnly; SameSite=Lax");
            userService.updateUserLoginTime(user.getId());
            redisService.setWhite(user.getId(), TokenEnum.FLUSH_TOKEN_HEADER, flushToken, 60 * 24);
            PrintWriter out = httpServletResponse.getWriter();
            Map<String, String> map = new HashMap<>(3);
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
        web.ignoring().antMatchers("/imgsave/**","/uploadImg//callback",
                "/RoleWebSocket/**", "/mass", "/UserWebSocket/**", "/ItemWebSocket/**", "/druid/**", "/getToken/**");

    }

}




