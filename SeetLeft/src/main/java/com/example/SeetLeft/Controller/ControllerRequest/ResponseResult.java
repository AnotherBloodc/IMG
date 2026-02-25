package com.example.SeetLeft.Controller.ControllerRequest;

import com.example.SeetLeft.Enums.ReturnEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> {

    @Schema(description = "请求编码：状态码")
    private Integer code;

    @Schema(description = "请求备注：提示信息")
    private String message;

    @Schema(description = "请求状态：是否成功")
    private Boolean success;

    @Schema(description = "请求结果：查询到的结果数据")
    private T data;

    /**
     * 成功
     * 包含数据
     * 默认备注
     *
     * @return 返回封装了请求状态，请求备注，请求编码，请求结果的Result对象
     */
    public static <T> ResponseResult<T> ok(T data) {
        //创建Result对象，用来封装用户状态数据
        return new ResponseResult<>(
                ReturnEnums.SUCCESS.getCode(),
                ReturnEnums.SUCCESS.getMessage(),
                ReturnEnums.SUCCESS.getSuccess(),
                data
        );
    }

    /**
     * 成功
     * 包含数据
     * 自定义备注
     *
     * @return 返回封装了请求状态，请求备注，请求编码，请求结果的Result对象
     */
    public static <T> ResponseResult<T> ok(T data, String message) {
        //创建Result对象，用来封装用户状态数据
        return new ResponseResult<>(
                ReturnEnums.SUCCESS.getCode(),
                message,
                ReturnEnums.SUCCESS.getSuccess(),
                data
        );
    }


    /**
     * 成功
     * 没有数据
     * 自定义备注
     *
     * @return 返回封装了请求状态，请求备注，请求编码，请求结果的Result对象
     */
    public static <T> ResponseResult<T> ok(String message) {
        //创建Result对象，用来封装用户状态数据
        return new ResponseResult<>(
                ReturnEnums.SUCCESS.getCode(),
                message,
                ReturnEnums.SUCCESS.getSuccess(),
                null
        );
    }

    /**
     * 错误
     * 自定义备注
     *
     * @return ReturnEnums 返回一个预设的错误结果对象，包含失败状态、错误码和错误信息
     */
    public static <T> ResponseResult<T> error(String message) {
        return new ResponseResult<>(
                ReturnEnums.SUCCESS.getCode(),
                message,
                ReturnEnums.SUCCESS.getSuccess(),
                null
        );
    }

    /**
     * 错误
     * 自定义代码
     * 自定义备注
     *
     * @return ReturnEnums 返回一个预设的错误结果对象，包含失败状态、错误码和错误信息
     */
    public static <T> ResponseResult<T> error(String message, Integer code) {
        return new ResponseResult<>(
                ReturnEnums.SUCCESS.getCode(),
                message,
                ReturnEnums.SUCCESS.getSuccess(),
                null
        );
    }


    /**
     * 错误
     * 默认备注
     *
     * @return ReturnEnums 返回一个预设的错误结果对象，包含失败状态、错误码和错误信息
     */
    public static <T> ResponseResult<T> error() {
        return new ResponseResult<>(
                ReturnEnums.SUCCESS.getCode(),
                ReturnEnums.SUCCESS.getMessage(),
                ReturnEnums.SUCCESS.getSuccess(),
                null
        );
    }
}