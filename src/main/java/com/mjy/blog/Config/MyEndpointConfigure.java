package com.mjy.blog.Config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.server.ServerEndpointConfig;

/**
 * @author mjy
 * @create 2020-03-19-21:33
 */
@Component
public class MyEndpointConfigure extends ServerEndpointConfig.Configurator  {
    private static volatile ApplicationContext context;

//    @Override
//    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
//        System.out.println(clazz);
//        System.out.println(context.getBean(clazz));
//        return context.getBean(clazz);
//    }
//1
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        System.out.println("---------------app 初始化");
//        MyEndpointConfigure.context = applicationContext;
//    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}

