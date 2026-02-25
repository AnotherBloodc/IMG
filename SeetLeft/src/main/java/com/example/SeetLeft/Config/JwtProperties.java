package com.example.SeetLeft.Config;

import com.example.SeetLeft.Enums.LoginEnums;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.jwt")
@Data
@Slf4j
public class JwtProperties {

    public JwtProperties() {
        log.info(LoginEnums.GO + "JwtProperties:JWT配置文件");
    }

    private String secret;
}