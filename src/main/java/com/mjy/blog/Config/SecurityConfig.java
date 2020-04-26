package com.mjy.blog.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjy.blog.Bean.Role;
import com.mjy.blog.Bean.User;
import com.mjy.blog.Filter.AuthenticationAccessDeniedHandler;
import com.mjy.blog.Filter.TokenFilter;
import com.mjy.blog.Service.UserService;
import com.mjy.blog.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private KeyConfig keyConfig;
    @Autowired
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
                .formLogin().successHandler(new AuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                Authentication authentication) throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                User principal = (User) authentication.getPrincipal();
                User user = new User();
                user.setUsername(principal.getUsername());
                user.setRoles(principal.getRoles());
                user.setId(principal.getId());
                String token = JwtUtils.generateTokenExpireInMinutes(user, keyConfig.getPrivateKey(), 24 * 60*7);
                httpServletResponse.addHeader("Authorization", "Bearer " + token);
                Cookie cookie = new Cookie("flushtoken", "mjygdsh");
                cookie.setPath("/webapp");
                cookie.setHttpOnly(true);
                httpServletResponse.addCookie(cookie);

                PrintWriter out = httpServletResponse.getWriter();
                HashMap map = new HashMap<>();
                map.put("status", "1");
                map.put("msg", "登录成功");
                map.put("username",user.getUsername());
                out.write(new ObjectMapper().writeValueAsString(map));
                out.flush();
                out.close();
            }
        }).failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                AuthenticationException e) throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                PrintWriter out = httpServletResponse.getWriter();
                out.write("{\"status\":\"0\",\"msg\":\"登录失败\"}");
                out.flush();
                out.close();
            }
        })

                .and()
                .csrf().disable().exceptionHandling().accessDeniedHandler(accessDeniedHandler);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/imgsave/**",
                "/RoleWebSocket/**","/mass","/UserWebSocket/**","/ItemWebSocket/**","/druid/**");

    }

}




