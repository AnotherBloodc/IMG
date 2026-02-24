package com.example.SeetLeft.Controller;

import com.example.SeetLeft.Config.RedisConfig;
import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Enums.LogH;
import com.example.SeetLeft.Service.Impl.LoginServiceImpl;
import com.example.SeetLeft.Tool.JwtUtils;
import com.example.SeetLeft.Tool.Return;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private final StringRedisTemplate stringRedisTemplate;

    private final JwtUtils jwtUtils;

    // 构造器注入（推荐的依赖注入方式）
    public LoginController(StringRedisTemplate stringRedisTemplate, LoginServiceImpl login, JwtUtils jwtUtils) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.login = login;
        this.jwtUtils = jwtUtils;
    }

    /**
     * 登录用户：JSON<br/>
     * 注解：@PostMapping: 用于设置传递过来的POST请求的访问路径
     *
     * @param user 用户对象，可为null
     * @return 登录操作的结果状态码
     */
    @PostMapping("/LoginUser")
    @Operation(summary = "用户登录", description = "提供User对象以及登录后的token")
    public Return<Object> login(
            @RequestBody User user,
            @RequestParam(value = "isLogin", required = false, defaultValue = "false") boolean isLogin) {
        log.info(LogH.GO);
        log.info("你正在执行用户登录，你提供的条件：" + user);
        //查询此用户
        User users = login.GetUser(user);
        //判断用户是否存在
        if (users == null) {
            log.info("你当前登录的数据不存在");
            log.info(LogH.EXIT);
            return Return.error("用户登录，没有此用户");
        }
        log.info("登录成功，判断自动登录的值：" + isLogin);
        if (isLogin) {
            log.info("开启自动登录，将生成token并且储存Redis");
            String token = JwtUtils.generateToken(users);
            log.info("token:{}", token);
            // 将token存入Redis（核心逻辑）
            try {
                // 设计Redis Key：前缀+用户名（方便后续查找/删除），比如 "token:zhangsan"
                String redisKey = "token:" + users.getUsername();
                // 设置token到Redis，并指定过期时间，参数：key, value, 过期时间, 时间单位
                stringRedisTemplate.opsForValue().set(redisKey, token, 2, TimeUnit.HOURS);
                log.info("用户的token已成功存入Redis，key:{}", redisKey);
                log.info(LogH.EXIT);
                return Return.ok(user, token);
            } catch (Exception e) {
                log.info("存入Redis失败", e);
                log.info(LogH.EXIT);
                return Return.error("登录失败：token存储异常");
            }
        }
        log.info(LogH.EXIT);
        return Return.ok(user, null);
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
        log.info("用户注册(post/register)：{}", "条件：");
        log.info("{}", user);
        if (login.SetUser(user) > 0) {
            log.info("用户注册成功");
            return Return.ok(user);
        }
        log.info("用户注册失败");
        return Return.error();
    }

}
