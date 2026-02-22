package com.example.SeetLeft.Tool;

import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Data
public class UserValidator {
    // 禁用的词汇列表
    private static final List<String> FORBIDDEN_WORDS = Arrays.asList(
            "fuck", "shit", "damn", "asshole", "bitch",
            "bastard", "crap", "hell", "stupid", "idiot",
            "垃圾", "笨蛋", "白痴", "傻逼", "蠢货",
            "废物", "混蛋", "王八", "狗屎", "放屁"
    );

    private Boolean username = false;
    private String userMsg = "用户名格式不通过";

    private Boolean password = false;
    private String passMsg = "密码不符合条件";


    /**
     * 验证用户名是否符合规则
     *
     * @param username 用户名
     * @return 验证结果对象
     */
    public UserValidator validateUser(String username) {
        if (username == null || (username = username.trim()).isEmpty()) {
            this.userMsg = "用户名不能为空";
            return this;
        } else if (getByteLength(username) > 16) {
            this.userMsg = "用户名长度不能超过16字节";
            return this;
        } else if (!username.matches("^[a-zA-Z0-9\\u4e00-\\u9fa5_]+$")) {
            this.userMsg = "用户名只能包含字母、数字、中文和下划线";
            return this;
        } else {
            String lowerUsername = username.toLowerCase();
            for (String forbiddenWord : FORBIDDEN_WORDS) {
                String lowerForbidden = forbiddenWord.toLowerCase();
                // 使用正则匹配整词，避免子串误判
                if (Pattern.compile("\\b" + Pattern.quote(lowerForbidden) + "\\b").matcher(lowerUsername).find()) {
                    this.userMsg = "用户名包含不当内容";
                    return this;
                }
            }
        }
        this.username = true;
        this.userMsg = "成功";
        return this;
    }

    public UserValidator validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            this.passMsg = "密码不能为空";
            return this;
        } else if (password.length() < 8 || password.length() > 16) {
            this.passMsg = "密码长度必须在8-16位之间";
            return this;
        } else if (!Character.isLetter(password.charAt(0))) {
            this.passMsg = "密码必须以字母开头";
            return this;
        } else if (!password.matches("^[a-zA-Z0-9_]+$")) {
            this.passMsg = "密码只能包含字母、数字和下划线";
            return this;
        } else {
            this.password = true;
            this.passMsg = "成功";
            return this;
        }
    }


    /**
     * 计算字符串的UTF-8字节长度
     */
    public static int getByteLength(String str) {
        return str.getBytes(StandardCharsets.UTF_8).length;
    }
}