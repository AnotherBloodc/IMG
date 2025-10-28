package com.example.SeetSelectUser.Tool;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Return {
    private Boolean success;//状态码
    private String message;//状态信息
    private Integer code;//过期时间
    private Map<String, String> data = new HashMap<>();//token键值对

    private Return() {
    }

    public static Return error() {
        Return r = new Return();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        System.out.println("data:" + r.data);
        return r;
    }

    public static Return ok(String key, String value) {
        Return r = new Return();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        r.data.put(key, value);
        System.out.println("data:" + r.data);
        return r;
    }
}
