package com.mjy.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author mjy
 * @create 2020-04-07-22:51
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Value("${imgPath}")
    private String path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imgsave/**").addResourceLocations("file:" + path);
    }

//    @Bean
//    public TomcatContextCustomizer sameSiteCookiesConfig() {
//        return context -> {
//            final Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
//            cookieProcessor.setSameSiteCookies(SameSiteCookies.LAX.getValue());
//            context.setCookieProcessor(cookieProcessor);
//        };
//    }
}

