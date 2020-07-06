package com.mjy.blog.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Exception.AccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author mjy
 * @create 2020-06-29-21:00
 */
@ControllerAdvice
public class GlobalDefultExceptionHandler {
    @ExceptionHandler(AccessException.class)
    public void defultExcepitonHandler(HttpServletRequest request, HttpServletResponse resp , Exception ex) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter out = resp.getWriter();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("Msg","请重新登陆");
        out.write(new ObjectMapper().writeValueAsString(stringStringHashMap));
        out.flush();
        out.close();

    }
}
