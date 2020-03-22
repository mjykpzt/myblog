package com.mjy.blog.Config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.websocket.server.ServerEndpointConfig;

/**
 * @author mjy
 * @create 2020-03-19-21:33
 */

public class MyEndpointConfigure extends ServerEndpointConfig.Configurator implements ApplicationContextAware {
    private static volatile ApplicationContext context;

    @Override
    public <T> T getEndpointInstance(Class<T> clazz){
        return context.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("---------------app 初始化");
        MyEndpointConfigure.context = applicationContext;
    }
}

