package com.example.SeetLeft.Tool;

import com.example.SeetLeft.Controller.ControllerRequest.ResponseResult;
import com.example.SeetLeft.Enums.LoginEnums;
import com.example.SeetLeft.Exception.BusinessExceptionConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class ControllerUtils {

    //包含数据
    public static <T> ResponseResult<T> messageHandler(Supplier<T> supplier) {
        log.info(LoginEnums.GO + "ResponseResult异常处理类:包含数据");
        try {
            T data = supplier.get();
            return ResponseResult.ok(data);
        } catch (BusinessExceptionConfig e) {
            log.warn("业务异常 - 代码: {}, 消息: {}", e.getCode(), e.getMessage());
            return ResponseResult.error(e.getMessage(), e.getCode());
        } catch (Exception e) {
            log.error("系统异常 - 错误信息: {}", e.getMessage(), e);
            return ResponseResult.error("服务器内部错误，请稍后重试", 500);
        }
    }



    //无数据返回统一操作
    public static ResponseResult<Void> messageHandler(Runnable runnable) {
        log.info(LoginEnums.GO + "ResponseResult异常处理类:无数据返回统一操作");
        try {
            runnable.run();
            return ResponseResult.ok(null);
        } catch (BusinessExceptionConfig e) {
            log.warn("业务异常 - 代码: {}, 消息: {}", e.getCode(), e.getMessage());
            return ResponseResult.error(e.getMessage(), e.getCode());
        } catch (Exception e) {
            log.error("系统异常 - 错误信息: {}", e.getMessage(), e);
            return ResponseResult.error("服务器内部错误，请稍后重试", 500);
        }
    }
}
