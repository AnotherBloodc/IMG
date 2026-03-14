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
            HttpServletResponse response,
            HttpServletRequest request) {
        log.info(LoginEnums.GO + "用户执行登录操作");

        //从Cookie中查询token,与Redis中的token对比看看是否匹配，若匹配成功则解析,正确且无过期则直接登录
        String tokenCookie = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    tokenCookie = cookie.getValue();
                    break;
                }
            }
        }
        //判断Cookie中是否有token
        if (tokenCookie != null) {
            try {
                log.info("成功，Cookie中存在该用户的token");
                User list = jwtUtils.getUserFromToken(tokenCookie);

                //用cookie中的token中的用户信息为条件，从Redis中获取Token并解析，得到其中的数据
                String token = stringRedisTemplate.opsForValue().get("token:" + list.getUsername());

                //判断Redis中的token是否为空
                if (token == null) {
                    log.warn("失败，没有从 Redis 中得到该用户的 token");
                    return Return.error("无登录凭证，请重新登录");
                }

                //判断Cookie中的token是否与Redis中的token一致
                if (!tokenCookie.equals(token)) {
                    log.warn("失败，Cookie 与 Redis 中的 token 不一致，可能已被替换");
                    return Return.error("登录凭证无效");
                }
                list = login.GetUser(list.getId(), list.getUsername());//通过token中的用户信息查询数据库
                log.info("成功，Redis中的Token与Cookie中的Token验证通过");
                return Return.ok(list, "自动登录");
            } catch (Exception e) {
                log.warn("失败，Token 验证失败（可能已过期或被篡改），继续执行普通登录流程", e);
            }
        }

        //token为空，通过用户提供的账号密码查询数据库
        if (user == null) {
            return Return.error("没有提供账号");
        }

        User list = login.GetUser(user);
        if (list == null) {
            log.warn("提供的账号不存在");
            return Return.error("提供的账号不存在");
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
    public Return<User> register(@RequestBody User user) {
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

        if (login.SetUser(user) > 0) {
            log.info("用户注册成功");
            return Return.ok(user, "注册成功");
        }
        log.info("用户注册失败");
        return Return.error("注册失败系统出错");
    }

}
