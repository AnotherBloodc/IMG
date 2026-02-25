package com.example.SeetLeft.Config;

import com.example.SeetLeft.Enums.LoginEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SwaggerConfig {
    public SwaggerConfig() {
        log.info(LoginEnums.GO + "SwaggerConfig:Swagger自定义配置文件");
    }
}
