package com.example.SeetLeft;

import com.example.SeetLeft.Enums.LoginEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@Slf4j
@SpringBootApplication
@EnableCaching
public class SeetLeftApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeetLeftApplication.class, args);
        log.info(LoginEnums.GO + "启动成功，我去你的");
    }


}
