package com.mjy.blog.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mjy
 * @create 2020-03-28-20:23
 */
@RestController
public class ItemWebSocket {
    /**
     * @param msg 客户端发来的信息
     * @return: java.lang.String
     * @author: 0205
     *
     * SendTo 发送至 Broker 下的指定订阅路径
     */

    @MessageMapping("/massRequest/item")
    @SendTo("/user/item/getResponse")
    public String mass(String msg){
        return "flush";
    }
}
