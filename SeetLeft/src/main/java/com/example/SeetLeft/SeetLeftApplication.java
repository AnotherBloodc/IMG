package com.example.SeetLeft;

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
    }

}
