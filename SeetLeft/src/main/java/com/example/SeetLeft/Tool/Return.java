package com.example.SeetLeft.Tool;

import com.example.SeetLeft.Enums.ReturnEnums;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Return<T> {

    @Schema(description = "请求编码：状态码")
    private Integer code;

    @Schema(description = "请求备注：提示信息")
    private String message;

    @Schema(description = "请求状态：是否成功")
    private Boolean success;

    @Schema(description = "请求结果：查询到的结果数据")
    private T data;

    @Schema(description = "请求结果：token")
    private String token;

    /**
     * 成功响应,返回结果：包含数据,包含Token
     *
     * @return 返回封装了成功状态信息的Result对象
     */
    public static <T> Return<T> ok(T data, String token) {
        //创建Result对象，用来封装用户状态数据
        return new Return<>(
                ReturnEnums.SUCCESS.getCode(),
                ReturnEnums.SUCCESS.getMessage(),
                ReturnEnums.SUCCESS.getSuccess(),
                data,
                token
        );
    }

    /**
     * 成功响应,返回结果:包含数据,不包含Token
     *
     * @return 返回封装了成功状态信息的Result对象
     */
    public static <T> Return<T> ok(T data) {
        //创建Result对象，用来封装用户状态数据
        return new Return<>(
                ReturnEnums.SUCCESS.getCode(),
                ReturnEnums.SUCCESS.getMessage(),
                ReturnEnums.SUCCESS.getSuccess(),
                data,
                null
        );
    }

    /**
     * 成功,返回结果不包含数据
     *
     * @return 返回封装了请求状态，请求备注，请求编码，请求结果的Result对象
     */
    public static <T> Return<T> ok() {
        //创建Result对象，用来封装用户状态数据
        return new Return<>(
                ReturnEnums.SUCCESS.getCode(),
                ReturnEnums.SUCCESS.getMessage(),
                ReturnEnums.SUCCESS.getSuccess(),
                null,
                null
        );
    }

    /**
     * 错误，返回结果不包含数据
     *
     * @return ReturnEnums 返回一个预设的错误结果对象，包含失败状态、错误码和错误信息
     */
    public static <T> Return<T> error() {
        return new Return<>(
                ReturnEnums.SUCCESS.getCode(),
                ReturnEnums.SUCCESS.getMessage(),
                ReturnEnums.SUCCESS.getSuccess(),
                null,
                null
        );
    }
    /**
     * 错误，返回结果不包含数据
     *
     * @return ReturnEnums 返回一个预设的错误结果对象，包含失败状态、错误码和错误信息
     */
    public static <T> Return<T> error(String message) {
        return new Return<>(
                ReturnEnums.SUCCESS.getCode(),
                message,
                ReturnEnums.SUCCESS.getSuccess(),
                null,
                null
        );
    }

}
