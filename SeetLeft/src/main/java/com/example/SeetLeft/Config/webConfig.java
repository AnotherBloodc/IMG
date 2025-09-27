package com.example.SeetLeft.Config;

import com.example.SeetLeft.Interceptor.Loginlnterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//配置类
@Configuration
public class webConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("注册拦截器");
        registry.addInterceptor(new Loginlnterceptor()).addPathPatterns("/Insert");
    }
}
