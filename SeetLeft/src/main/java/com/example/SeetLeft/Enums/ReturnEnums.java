package com.example.SeetLeft.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReturnEnums {
    //成功
    SUCCESS(2000, "success", true),

    //失败
    FAILURE(5000, "failure", false);

    /**
     * code
     */
    private final Integer code;

    /**
     * Msg
     */
    private final String msg;

    /**
     * success
     */
    private final Boolean success;
}
