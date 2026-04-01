package com.example.SeetLeft.Handler;

import com.example.SeetLeft.Enums.LoginEnums;
import com.example.SeetLeft.Exception.BusinessExceptionConfig;
import com.example.SeetLeft.Tool.Return;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class PublicExceptionHandler {
    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(BusinessExceptionConfig.class)
    @ResponseBody
    public ResponseEntity<Return<Void>> handleBusinessException(BusinessExceptionConfig e) {
        log.warn(LoginEnums.GO + "业务异常 - code: {}, message: {}", e.getCode(), e.getMessage());
        Return<Void> result = Return.error(e.getMessage());
        return ResponseEntity.status(e.getCode()).body(result);
    }

    /**
     * 处理参数校验失败（@Valid / @Validated）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Return<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMsg = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorMsg.append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append("; ")
        );

        log.warn("参数验证失败: {}", errorMsg);
        Return<Void> result = Return.error("参数验证失败: " + errorMsg);
        return ResponseEntity.badRequest().body(result);
    }

    /**
     * 兜底处理所有未捕获异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Return<Void>> handleException(Exception e) {
        log.error("系统未捕获异常", e);
        Return<Void> result = Return.error("服务器内部错误，请稍后重试");
        return ResponseEntity.internalServerError().body(result);
    }
}