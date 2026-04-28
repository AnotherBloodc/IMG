package com.example.SeetLeft.Controller;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Enums.LoginEnums;
import com.example.SeetLeft.Service.Impl.LoginServiceImpl;
import com.example.SeetLeft.Tool.JwtUtils;
import com.example.SeetLeft.Tool.Return;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;


@CrossOrigin
@RestController
@RequestMapping("/SeetLogin")
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

    @Value("${server.ssl.enabled:false}")
    private boolean sslEnabled;


    /**
     * 登录页面：用于前端访问<br/>
     * 注解：@GetMapping: 用于设置传递过来的 GET 请求的访问路径
     *
     * @return 空字符串，表示这是一个有效的端点
     */
    @GetMapping
    @Operation(summary = "登录界面测试", description = "前端访问登录页面")
    public Return<Object> loginPage() {
        log.info(LoginEnums.GO + "访问登录页面");
        return Return.ok("测试接口");
    }

    /**
     * 登录用户：JSON<br/>
     * 注解：@PostMapping: 用于设置传递过来的POST请求的访问路径
     *
     * @param user      用户对象，可为null
     * @param AutoLogin 是否自动登录
     * @return 登录操作的结果状态码
     */
    @PostMapping("/LoginUser")
    @Operation(summary = "用户登录", description = "支持主动登录（账号密码）和自动登录（Token）")
    public Return<Object> login(
            @RequestBody(required = false) User user,
            @RequestParam(required = false, value = "AutoLogin", defaultValue = "false") boolean AutoLogin,
            HttpServletResponse response) {
        log.info(LoginEnums.GO + "用户执行登录操作");
        //判断账号密码是否存在
        if (user == null) {
            return Return.error("没有提供账号");
        }


        // 检查是否被锁定
        String lockKey = "login:lock:" + user.getUsername();
        if (stringRedisTemplate.hasKey(lockKey)) {
            return Return.error("登录失败次数过多，请15分钟后再试");
        }

        //执行登录操作，查询此用户
        User list = login.GetUser(user);
        //判断结果是否为空
        if (list == null) {
            // 记录失败次数
            String attemptsKey = "login:attempts:" + user.getUsername();
            Long attempts = stringRedisTemplate.opsForValue().increment(attemptsKey);
            if (attempts >= LoginEnums.MAX_LOGIN_ATTEMPTS) {
                stringRedisTemplate.opsForValue().set(
                        lockKey,
                        "locked",
                        LoginEnums.LOCK_DURATION_MINUTES,
                        TimeUnit.MINUTES);
                return Return.error("登录失败次数过多，请15分钟后再试");
            }
            log.warn("登录失败 - 账号或密码错误");
            return Return.error("账号或密码错误");
        }

        stringRedisTemplate.delete("login:attempts:" + user.getUsername());

        //执行token生成
        String token;
        token = jwtUtils.generateToken(list);

        //设置redis过期时间：30天，普通：2小时
        long expireHours = AutoLogin ? 720 : 2;
        int cookieMaxAge = AutoLogin ? (int) (expireHours * 3600) : -1;
        // 设计Redis Key：前缀+用户名（方便后续查找/删除），比如 "token:zhangsan"
        String redisKey = "token:" + list.getUsername();

        // 保存token到Redis，并指定过期时间，参数：key, value, 过期时间, 时间单位
        stringRedisTemplate.opsForValue().set(redisKey, token, expireHours, TimeUnit.HOURS);

        // 保存token到Cookie,设置HttpOnly Cookie（核心：防XSS）
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true); // 前端JS无法读取，防XSS
        cookie.setSecure(sslEnabled); // 仅HTTPS传输（生产环境开启，本地测试可注释）
        cookie.setPath("/"); // 全站所有接口都能携带这个Cookie
        cookie.setMaxAge(cookieMaxAge); // Cookie过期时间（和Redis Token一致，30day）
        response.addCookie(cookie);// 把Cookie返回给客户端
        log.info("登录成功，用户的token已成功存入Redis和Cookie");
        return Return.ok(list, "登录成功", token);
    }


    /**
     * 登出用户：JSON<br/>
     * 注解：@GetMapping: 用于设置传递过来的 GET 请求的访问路径
     *
     * @return 登出操作的结果状态码
     */
    @PostMapping("/LoginExit")
    @Operation(summary = "用户登出", description = "注销登录，需要携带 Token")
    public Return<Object> loginExit(
            HttpServletRequest request,
            HttpServletResponse response) {
        log.info(LoginEnums.GO + "用户登出");

        // 从 Cookie 获取 Token
        String tokenFromCookie = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    tokenFromCookie = cookie.getValue();
                    break;
                }
            }
        }

        if (tokenFromCookie == null || tokenFromCookie.isEmpty()) {
            return Return.error("未找到登录凭证");
        }

        try {
            // 解析 Token 得到用户名
            User tokenUser = jwtUtils.getUserFromToken(tokenFromCookie);
            String username = tokenUser.getUsername();

            // 删除 Redis 中的 token
            String redisKey = "token:" + username;
            stringRedisTemplate.delete(redisKey);

            // 清除 Cookie
            Cookie cookie = new Cookie("token", null);
            cookie.setPath("/");
            cookie.setMaxAge(0); // 立即过期
            response.addCookie(cookie);

            log.info("用户已登出，Redis 中的 token 已删除，key:{}", redisKey);
            return Return.ok("账号退出成功");

        } catch (Exception e) {
            log.warn("Token 无效，登出失败", e);
            return Return.error("无效的登录凭证");
        }
    }

    /**
     * 注册用户：JSON<br/>
     * 注解：@PostMapping: 用于设置传递过来的 POST 请求的访问路径
     *
     * @param user 用户对象，可为 null
     * @return 插入操作的结果状态码
     */
    @PostMapping("/Register")
    @Operation(summary = "用户注册", description = "提供 User 对象")
    public Return<Integer> register(@RequestBody User user) {
        log.info(LoginEnums.GO + "用户注册");

        // 添加参数校验
        if (user == null) {
            return Return.error("用户信息不能为空");
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return Return.error("用户名不能为空");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return Return.error("密码不能为空");
        }

        int list = login.SetUser(user);
        if (list < 1) {
            return Return.error("注册失败系统出错");
        }
        Return<Integer> R = ProjectExceptionAdvice.messageHandler(() -> list);
        log.info("用户注册成功");
        return R;
    }

}
