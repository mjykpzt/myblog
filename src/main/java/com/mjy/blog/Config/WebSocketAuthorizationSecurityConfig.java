package com.mjy.blog.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

/**
 * @author mjy
 * @create 2020-03-26-0:42
 */
@Configuration
public class WebSocketAuthorizationSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
    @Override
    protected void configureInbound(final MessageSecurityMetadataSourceRegistry messages) {
        messages.simpDestMatchers("/user/**").hasAnyRole("ADMIN","USER")
                .simpDestMatchers("/admin/**").hasAnyRole("ADMIN").anyMessage().authenticated();
    }


    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
