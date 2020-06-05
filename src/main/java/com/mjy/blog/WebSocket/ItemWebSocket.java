package com.mjy.blog.WebSocket;

import com.alibaba.druid.support.logging.Log4jImpl;
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
     * @param msg
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
