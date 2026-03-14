package com.example.SeetLeft.Config;

import com.example.SeetLeft.Enums.LoginEnums;
import com.example.SeetLeft.Interceptor.LoginLinterCeptor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    public WebConfig() {
        log.info(LoginEnums.GO + "WebConfig拦截器配置文件");
    }

    @Resource
    private LoginLinterCeptor loginLinterCeptor;

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info(LoginEnums.GO + "添加拦截器");
        //添加拦截器，并添加拦截路径
        registry
                .addInterceptor(loginLinterCeptor)//选中拦截器
                .addPathPatterns("/Insert")//指定拦截请求
                .excludePathPatterns("/Login");//添加排除请求
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info(LoginEnums.GO + "添加允许的请求方式");
        registry.addMapping("/**")//允许所有请求
                .allowedMethods("GET", "POST", "PUT", "DELETE")//允许所有请求方式
                .allowedHeaders("*")//允许所有请求头
                .maxAge(120);//请求时间：秒
    }
}