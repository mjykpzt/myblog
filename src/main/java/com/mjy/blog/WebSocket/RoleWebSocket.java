package com.mjy.blog.Service.WebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author mjy
 * @create 2020-03-19-21:40
 */
@RestController
@Secured("ROLE_ADMIN")
public class RoleWebSocket{

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/massRequest")
    //SendTo 发送至 Broker 下的指定订阅路径
    @SendTo("/mass/getResponse")
    public String mass(String msg, @Headers Map<String, Object> headers){
        System.out.println(msg);
        return "flush";
    }

}

