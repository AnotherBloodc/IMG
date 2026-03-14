package com.example.SeetLeft.Controller;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Enums.LoginEnums;
import com.example.SeetLeft.Service.Impl.LoginServiceImpl;
import com.example.SeetLeft.Tool.JwtUtils;
import com.example.SeetLeft.Tool.Return;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;


@CrossOrigin
@RestController
@RequestMapping("/Login")
@Slf4j
@Tag(name = "用户操作：登录与注册", description = "用户执行登录操作")
public class LoginController {

    @Resource
    private LoginServiceImpl login;

    // 注入Redis操作模板（Spring会自动装配，前提是Redis配置正确）
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private JwtUtils jwtUtils;

    /**
     * 登录用户：JSON<br/>
     * 注解：@PostMapping: 用于设置传递过来的POST请求的访问路径
     *
     * @param user    用户对象，可为null
     * @param isLogin 是否自动登录
     * @return 登录操作的结果状态码
     */
    @PostMapping("/LoginUser")
    @Operation(summary = "用户登录", description = "支持主动登录（账号密码）和自动登录（Token）")
    public Return<Object> login(
            @RequestBody(required = false) User user,
            @RequestParam(required = false, value = "isLogin", defaultValue = "false") boolean isLogin,
            HttpServletResponse response) {
        log.info(LoginEnums.GO + "执行用户登录");

        //从Redis中查询token并解析,如果正确则直接登录
        String tokens = stringRedisTemplate.opsForValue().get("token:" + user.getUsername());
        if (tokens != null) {
            log.info("成功从Redis获取token，通过token获取用户信息查询数据库");
            User list = jwtUtils.getUserFromToken(tokens);
            list = login.GetUser(list.getId(), list.getUsername());
            log.info("自动登录成功");
            return Return.ok(list, "自动登录成功");
        }

        //查询此用户并判断是否存在
        User list = login.GetUser(user);
        if (list == null) {
            return Return.error("当前账号不存在");
        }

        //判断是否需要自动登录
        if (isLogin) {

            //生成token
            log.info("执行自动登录保存用户信息");
            String token = jwtUtils.generateToken(list);
            try {
                // 设计Redis Key：前缀+用户名（方便后续查找/删除），比如 "token:zhangsan"
                String redisKey = "token:" + list.getUsername();

                // 保存token到Redis，并指定过期时间，参数：key, value, 过期时间, 时间单位
                stringRedisTemplate.opsForValue().set(redisKey, token, 2, TimeUnit.HOURS);

                // 保存token到Cookie,设置HttpOnly Cookie（核心：防XSS）
                Cookie cookie = new Cookie("token", token);
                cookie.setHttpOnly(true); // 前端JS无法读取，防XSS
                cookie.setSecure(true); // 仅HTTPS传输（生产环境开启，本地测试可注释）
                cookie.setPath("/"); // 全站所有接口都能携带这个Cookie
                cookie.setMaxAge(2 * 3600); // Cookie过期时间（和Redis Token一致，2小时）

                // 把Cookie返回给客户端
                response.addCookie(cookie);
                log.info("用户的token已成功存入Redis和Cookie");
            } catch (Exception e) {
                log.info("用户信息保存失败", e);
                return Return.error("自动登录信息保存失败");
            }
        }
        log.info("登录成功");
        return Return.ok(list, "登录成功");
    }


    /**
     * 登出用户：JSON<br/>
     * 注解：@GetMapping: 用于设置传递过来的GET请求的访问路径
     *
     * @param username 用户名
     * @return 登出操作的结果状态码
     */
    @GetMapping("/LoginExit")
    @Operation(summary = "用户登出", description = "注销登录")
    public Return<Object> loginExit(String username) {
        log.info(LoginEnums.GO + "用户登出");
        log.info("用户名：{}", username);
        // 删除Redis中的token
        String redisKey = "token:" + username;
        stringRedisTemplate.delete(redisKey);
        log.info("用户已登出，Redis中的token已删除，key:{}", redisKey);
        return Return.ok("账号退出成功");
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
        log.info(LoginEnums.GO + "用户注册");
        if (login.SetUser(user) > 0) {
            log.info("用户注册成功");
            return Return.ok(user, "注册成功");
        }
        log.info("用户注册失败");
        return Return.error("注册失败系统出错");
    }

}
