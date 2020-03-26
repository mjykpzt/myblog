package com.mjy.blog.WebSocket;

import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author mjy
 * @create 2020-03-26-19:02
 */
@RestController
public class UserWebSocket {

    @MessageMapping("/massRequest/user")
    //SendTo 发送至 Broker 下的指定订阅路径
    @SendTo("/mass/user/getResponse")
    public String mass(String msg){
        return "flush";
    }
}
