package com.example.SeetLeft.Config;

import com.example.SeetLeft.Interceptor.LoginLinterCeptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private LoginLinterCeptor loginLinterCeptor;

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("注册拦截器");
        //添加拦截器，并添加拦截路径
        registry
                .addInterceptor(loginLinterCeptor)//选中拦截器
                .addPathPatterns("/Insert")//指定拦截请求
                .excludePathPatterns("/Login");//添加排除请求
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//允许所有请求
                .allowedMethods("GET", "POST", "PUT", "DELETE")//允许所有请求方式
                .allowedHeaders("*")//允许所有请求头
                .maxAge(120);//请求时间：秒
    }
}