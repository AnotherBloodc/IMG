package com.example.SeetLeft.Config;

import com.example.SeetLeft.Interceptor.Loginlnterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class webConfig implements WebMvcConfigurer {
    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("注册拦截器");
        //添加拦截器，并添加拦截路径
        registry.addInterceptor(new Loginlnterceptor()).addPathPatterns("/Insert");
    }
}
