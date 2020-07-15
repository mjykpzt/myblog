package com.mjy.blog.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mjy
 * @create 2020-03-26-19:02
 */
@RestController
public class UserWebSocket {
    /**
     * @param msg
     * @return: java.lang.String
     * @author: 0205
     *
     * SendTo 发送至 Broker 下的指定订阅路径
     */

    @MessageMapping("/massRequest/user")
    @SendTo("/admin/user/getResponse")
    public String mass(String msg){
        return "flush";
    }
}
