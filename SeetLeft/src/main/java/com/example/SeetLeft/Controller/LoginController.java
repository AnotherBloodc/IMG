package com.example.SeetLeft.Controller;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Service.Impl.LoginServiceImpl;
import com.example.SeetLeft.Tool.JwtUtils;
import com.example.SeetLeft.Tool.Return;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/Login")
@Slf4j
@Tag(name = "用户操作:登录与注册", description = "用户执行登录操作")
public class LoginController {

    @Resource
    private LoginServiceImpl login;

    /**
     * 登录用户：JSON<br/>
     * 注解：@PostMapping: 用于设置传递过来的POST请求的访问路径
     *
     * @param user 用户对象，可为null
     * @return 登录操作的结果状态码
     */
    @PostMapping("/LoginUser")
    @Operation(summary = "用户登录", description = "提供User对象以及登录后的token")
    public Return<Object> login(@RequestBody User user) {
        log.info("\n\n用户登录(/login){}", "\n条件:");
        log.info("登录详细user:{}", user);
        User users = login.GetUser(user);
        String token = JwtUtils.generateToken(users.getUsername());
        return Return.ok(user, token);
    }

    /**
     * 注册用户：JSON<br/>
     * 注解：@PostMapping: 用于设置传递过来的POST请求的访问路径
     *
     * @param user 用户对象，可为null
     * @return 插入操作的结果状态码
     */
    @PostMapping("/Register")
    @Operation(summary = "用户注册", description = "提供User对象")
    public Return<User> register(@RequestBody User user) {
        log.info("\n\n用户注册(post/register)：{}", "\n条件=" + user + "\n结果=");
        if (login.SetUser(user) > 0) {
            return Return.ok(user);
        }
        return Return.error();
    }

}
