package com.example.SeetLeft.Controller.Interceptor;


import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Enums.LoginEnums;
import com.example.SeetLeft.Tool.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.TimeUnit;

//HandlerInterceptor：Spring MVC框架提供的拦截器接口
@Slf4j
@Component //设置此类受Spring容器管理，标识一个由 Spring 管理的组件（Bean）
public class LoginLinterCeptor implements HandlerInterceptor {

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public LoginLinterCeptor() {
        log.info(LoginEnums.GO + "6.(自定义配置)LoginLinterCeptor拦截器初始化");
    }

    /**
     * 拦截器：带有拦截器的接口被请求访问时，会先经过拦截器，再经过控制器，最后经过视图解析器，返回给前端
     * preHandle：拦截指定请求之前，会运行的方法，返回true则放行，返回false则拦截
     *
     * @param request  接收前端请求对象
     * @param response 响应前端请求对象
     * @param handler  处理器对象
     * @return true：放行，false：拦截
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        log.info("拦截器preHandle：拦截指定请求之前，会运行的方法");

        //从Cookie中查询token,与Redis中的token对比看看是否匹配，若匹配成功则解析,正确且无过期则直接登录
        String tokenCookie = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    tokenCookie = cookie.getValue();
                    log.info("Cookie中存在该用户的token");
                    break;
                }
            }
        }
        //判断Cookie中是否存在token
        if (tokenCookie == null || tokenCookie.isEmpty()) {
            log.warn("拦截，Cookie中不存在token");
            return false;
        }
        log.info("已成功获取Cookie中的token");

        //从token中解析出用户信息
        User list = jwtUtils.getUserFromToken(tokenCookie);
        String redisKey = "token:" + list.getUsername();
        //用cookie中的token中的用户信息为条件，从Redis中获取Token并解析，得到其中的数据
        String tokenRedis = stringRedisTemplate.opsForValue().get(redisKey);

        //判断Redis中的token是否为空
        if (tokenRedis == null) {
            log.warn("拦截，没有从 Redis 中得到该用户的 token");
            return false;
        }

        //判断Cookie中的token是否与Redis中的token一致
        if (!tokenCookie.equals(tokenRedis)) {
            log.warn("拦截，Cookie 与 Redis 中的 token 不一致，可能已被替换");
            return false;
        }
        stringRedisTemplate.expire(redisKey, 2, TimeUnit.HOURS);
        log.info("登录成功 - 用户名：{}, IP：{}, UserAgent：{}",
                list.getUsername(),
                request.getRemoteAddr(),
                request.getHeader("User-Agent"));
        return true;
    }

    /**
     * 拦截器：拦截指定请求之后，会运行该方法
     *
     * @param request      接收前端请求对象
     * @param response     响应前端请求对象
     * @param handler      处理器对象
     * @param modelAndView 模型和视图对象
     * @throws Exception 抛出的异常
     */
    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        log.info("拦截器postHandle：拦截指定请求之后，会运行该方法");
    }

    /**
     * 拦截器：拦截指定请求之后，处理完毕视图渲染之后，会运行该方法
     *
     * @param request  接收前端请求对象
     * @param response 响应前端请求对象
     * @param handler  处理器对象
     * @param ex       抛出的异常
     * @throws Exception 抛出的异常
     */
    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, @Nullable Exception ex) throws Exception {
        log.info("拦截器afterCompletion：拦截指定请求之后，处理完毕视图渲染之后，会运行该方法");
    }

}