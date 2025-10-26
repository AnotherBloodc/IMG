package com.example.SeetLeft.Tool;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {
    private Boolean success;
    private String message;
    private Integer code;
    private Map<String, Object> data = new HashMap<>();

    //重写构造方法，将构造方法私有化
    private Result() {
    }

    /**
     * 向Result对象中的data属性添加token数据键值对
     *
     * @param key   数据的键名
     * @param value 数据的值
     * @return 返回当前Result对象，支持链式调用
     */
    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 创建一个表示操作成功的Result对象
     *
     * @return 返回封装了成功状态信息的Result对象
     */
    public static Result ok() {
        Result r = new Result();//创建Result对象，用来封装用户状态数据
        r.setSuccess(true);//设置状态码为真，代表成功
        r.setCode(ResultCode.SUCCESS);//设置状态数据为成功状态码
        r.setMessage("成功");
        return r;
    }

    /**
     * 创建一个表示错误结果的Result对象
     *
     * @return Result 返回一个预设的错误结果对象，包含失败状态、错误码和错误信息
     */
    public static Result error() {
        Result r = new Result();
        r.setSuccess(false);//设置状态码为假，代表失败
        r.setCode(ResultCode.ERROR);//设置状态数据为失败状态码
        r.setMessage("失败");
        return r;
    }
}
