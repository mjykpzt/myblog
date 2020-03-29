package com.mjy.blog.WebSocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mjy
 * @create 2020-03-28-20:23
 */
@RestController
public class ItemWebSocket {
    @MessageMapping("/massRequest/item")
    //SendTo 发送至 Broker 下的指定订阅路径
    @SendTo("/mass/item/getResponse")
    public String mass(String msg){
        return "flush";
    }
}
