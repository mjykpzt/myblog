package com.mjy.blog.WebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mjy
 * @create 2020-03-19-21:40
 */
@RestController
public class RoleWebSocket{

    /**
     * @param msg
     * @return: java.lang.String
     * @author: 0205
     *
     * SendTo 发送至 Broker 下的指定订阅路径
     */
    @MessageMapping("/massRequest/role")
    @SendTo("/admin/role/getResponse")
    public String mass(String msg){
        return "flush";
    }

}

