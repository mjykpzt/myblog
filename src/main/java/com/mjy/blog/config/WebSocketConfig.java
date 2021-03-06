package com.mjy.blog.config;

import com.mjy.blog.bean.TokenEnum;
import com.mjy.blog.bean.User;
import com.mjy.blog.utils.JwtUtils;
import com.mjy.blog.utils.Payload;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mjy
 * @create 2020-03-25-16:11
 */
@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Resource
    private KeyConfig keyConfig;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/RoleWebSocket","/UserWebSocket","/ItemWebSocket").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //点对点应配置一个/user消息代理，广播式应配置一个/topic消息代理
        registry.enableSimpleBroker("/topic", "/user", "/admin", "/mass");
        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
        registry.setUserDestinationPrefix("/user");
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        ChannelInterceptor channelInterceptor = new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (accessor!=null&&StompCommand.CONNECT == accessor.getCommand()) {
                    List<String> authorization = accessor.getNativeHeader(TokenEnum.AUTHORIZATION_TOKEN_HEADER);
                    assert authorization != null;
                    String token =authorization.get(0);
                    token = token.replace(TokenEnum.AUTHORIZATION_TOKEN_FLAG, "");
                    Payload<User> infoFromToken = JwtUtils.getInfoFromToken(token, keyConfig.getPublicKey(), User.class);
                    User user = infoFromToken.getUserInfo();
                    UsernamePasswordAuthenticationToken t1
                            = new UsernamePasswordAuthenticationToken
                            (user.getUsername(),null, user.getAuthorities());
                    accessor.setUser(t1);
                }
                return message;
            }
        };
        registration.interceptors(channelInterceptor);
    }

}

