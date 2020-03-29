package com.mjy.blog.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjy.blog.Bean.Role;
import com.mjy.blog.Bean.User;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

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
//    @Autowired
//    private DataSource dataSource;

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
                User user = new User();
                user.setUsername(authentication.getName());
                user.setRoles((List<Role>) authentication.getAuthorities());
                String token = JwtUtils.generateTokenExpireInMinutes(user, keyConfig.getPrivateKey(), 24 * 60);
                httpServletResponse.addHeader("Authorization", "Bearer " + token);
                PrintWriter out = httpServletResponse.getWriter();
                HashMap map = new HashMap<>();
                map.put("status", "1");
                map.put("msg", "登录成功");
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
//                .and().
//                rememberMe().userDetailsService(userService).
//                tokenRepository(persistentTokenRepository()).tokenValiditySeconds(120)

                .and()
                .csrf().disable();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/css/**","/js/**","/fonts/**","/favicon.ico",
                "/RoleWebSocket/**","/mass","/UserWebSocket/**","/ItemWebSocket/**");
//        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());

    }

//    @Bean
//    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
//        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
//        firewall.setAllowUrlEncodedSlash(true);
//        return firewall;
//    }


//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        tokenRepository.setDataSource(dataSource); // 设置数据源
////        tokenRepository.setCreateTableOnStartup(true); // 启动创建表，创建成功后注释掉
//        return tokenRepository;
//    }
}
