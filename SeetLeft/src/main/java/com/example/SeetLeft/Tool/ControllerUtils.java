package com.example.SeetLeft.Tool;

import com.example.SeetLeft.Enums.LoginEnums;
import com.example.SeetLeft.Exception.BusinessExceptionConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;


//统一处理异常
@Slf4j
public class ControllerUtils {

    //包含数据
    public static <T> Return<T> messageHandler(Supplier<T> supplier) {
        log.info(LoginEnums.GO + "ControllerUtils异常处理类:包含数据");
        try {
            T data = supplier.get();
            return Return.ok(data);
        } catch (BusinessExceptionConfig e) {
            log.warn("有返回结果业务异常 - 代码: {}, 消息: {}", e.getCode(), e.getMessage());
            return Return.error(e.getMessage());
        } catch (Exception e) {
            log.error("系统异常 - 错误信息: {}", e.getMessage(), e);
            return Return.error("服务器内部错误，请稍后重试");
        }
    }


    //无数据返回统一操作
    public static Return<Void> messageHandler(Runnable runnable) {
        log.info(LoginEnums.GO + "ResponseResult异常处理类:无数据返回统一操作");
        try {
            runnable.run();
            return Return.ok(null);
        } catch (BusinessExceptionConfig e) {
            log.warn("无返回结果业务异常 - 代码: {}, 消息: {}", e.getCode(), e.getMessage());
            return Return.error(e.getMessage());
        } catch (Exception e) {
            log.error("系统异常 - 错误信息: {}", e.getMessage(), e);
            return Return.error("服务器内部错误，请稍后重试");
        }
    }
}
