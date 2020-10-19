package com.mjy.blog.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author mjy
 * @create 2020-06-29-21:00
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {



    @ExceptionHandler(SQLException.class)
    public void defaultExcepitonHandler(HttpServletRequest request, HttpServletResponse resp , Exception ex) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
        PrintWriter out = resp.getWriter();
        HashMap<String, String> stringStringHashMap = new HashMap<>(3);
        stringStringHashMap.put("msg","服务器开小差了");
        out.write(new ObjectMapper().writeValueAsString(stringStringHashMap));
        out.flush();
        out.close();

    }
}
