package com.mjy.blog.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjy.blog.bean.TokenEnum;
import com.mjy.blog.bean.User;
import com.mjy.blog.config.KeyConfig;
import com.mjy.blog.service.RedisService;
import com.mjy.blog.utils.JwtUtils;
import com.mjy.blog.utils.Payload;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author mjy
 * @create 2020-03-24-14:04
 */
public class TokenFilter extends BasicAuthenticationFilter {
    private KeyConfig keyConfig;
    private RedisService redisService;



    public TokenFilter(AuthenticationManager authenticationManager, KeyConfig keyConfig, RedisService redisService) {
        super(authenticationManager);
        this.keyConfig = keyConfig;
        this.redisService = redisService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String authorization;
        authorization = request.getHeader(TokenEnum.AUTHORIZATION_TOKEN_HEADER);
        if (authorization != null && authorization.startsWith(TokenEnum.AUTHORIZATION_TOKEN_FLAG)) {
            String token = authorization.replace(TokenEnum.AUTHORIZATION_TOKEN_FLAG, "");
            Payload<User> infoFromToken = JwtUtils.getInfoFromToken(token, keyConfig.getPublicKey(), User.class);
            User user = infoFromToken.getUserInfo();
            if (user != null) {
                if (redisService.findTokenInBlack(user.getId(), token)) {
                    FailMsg(request, response, chain, "token无效");
                }
                request.setAttribute("uid", user.getId());
                request.setAttribute("role", user.getAuthorities());
                request.setAttribute("username", user.getUsername());
                UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authResult);
                chain.doFilter(request, response);
            } else {
                FailMsg(request, response, chain, "token无效");
            }
        } else {
            FailMsg(request, response, chain, "请登录");
        }
    }

    private void FailMsg(HttpServletRequest request, HttpServletResponse response, FilterChain chain, String msg)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter out = response.getWriter();
        HashMap<String, String> resultMap = new HashMap<>(3);
        resultMap.put("status", "0");
        resultMap.put("msg", msg);
        out.write(new ObjectMapper().writeValueAsString(resultMap));
        out.flush();
        out.close();
        chain.doFilter(request, response);
    }

}

