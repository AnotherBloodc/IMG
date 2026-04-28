package com.example.SeetLeft.Controller;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Enums.LoginEnums;
import com.example.SeetLeft.Exception.BusinessExceptionConfig;
import com.example.SeetLeft.Tool.Return;
import org.springframework.web.bind.annotation.ControllerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.function.Supplier;

/**
 * 异常处理器类：负责全部异常的处理
 */
@Slf4j
@ControllerAdvice
public class ProjectExceptionAdvice {

    public ProjectExceptionAdvice() {
        log.info(LoginEnums.GO + "异常处理类加载完毕");
    }

    @ExceptionHandler
    public Return<User> doException(Exception e) {
        log.info(LoginEnums.GO + "异常：+", e);
        return new Return<User>(501, "服务器异常", false, null, null);
    }


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


    //无数据
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
