package com.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebmvcConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置跨域请求
        registry.addMapping("/**")//允许跨域请求地址
                .allowedOriginPatterns("*")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
        //允许跨域请求的域名
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowCredentials(true)
//                .maxAge(3600);
    }
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns("/article/add")
//                .addPathPatterns("/comment/add");
//        WebMvcConfigurer.super.addInterceptors(registry);
//    }
}
