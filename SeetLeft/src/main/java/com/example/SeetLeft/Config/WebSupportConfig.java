package com.example.SeetLeft.Config;

import com.example.SeetLeft.Controller.Interceptor.LoginLinterCeptor;
import com.example.SeetLeft.Enums.LoginEnums;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration//标记配置类,支持类级别的元数据配置，定义 Bean 的配置类
public class WebSupportConfig implements WebMvcConfigurer {
    public WebSupportConfig() {
        log.info(LoginEnums.GO + "(Spring配置) WebConfig配置拦截器");
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
                .addPathPatterns("/Insert");//指定拦截请求
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info(LoginEnums.GO + "添加允许的请求方式");
        registry.addMapping("/**")//允许所有请求
                .allowedOriginPatterns("*")//允许所有源 (比 allowedOrigins 更灵活)
                .allowedMethods("GET", "POST", "PUT", "DELETE")//允许所有请求方式
                .allowedHeaders("*")//允许所有请求头
                .allowCredentials(true)//允许携带 Cookie
                .maxAge(120);//请求时间：秒
    }
}
