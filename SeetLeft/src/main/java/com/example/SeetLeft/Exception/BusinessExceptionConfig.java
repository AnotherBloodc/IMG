package com.example.SeetLeft.Exception;

import com.example.SeetLeft.Enums.LoginEnums;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusinessExceptionConfig extends RuntimeException {


    private final int code;

    public BusinessExceptionConfig(int code, String message) {
        super(message);
        log.info(LoginEnums.GO + "BusinessExceptionConfig");
        this.code = code;
    }

    public static BusinessExceptionConfig of(int code, String message) {
        return new BusinessExceptionConfig(code, message);
    }

    public static BusinessExceptionConfig badRequest(String message) {
        return new BusinessExceptionConfig(400, message);
    }

    public static BusinessExceptionConfig internalServerError(String message) {
        return new BusinessExceptionConfig(500, message);
    }

    public int getCode() {
        return code;
    }
}