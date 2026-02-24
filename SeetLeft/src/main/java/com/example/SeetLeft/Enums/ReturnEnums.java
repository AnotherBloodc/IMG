package com.example.SeetLeft.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReturnEnums {
    //成功
    SUCCESS(2000, "成功", true),

    //不成功
    FAILURE(5000, "不成功", false);

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 提示信息
     */
    private final String message;

    /**
     * 成功状态
     */
    private final Boolean success;
}
