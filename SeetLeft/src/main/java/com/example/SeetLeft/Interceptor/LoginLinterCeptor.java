package com.example.SeetLeft.Interceptor;


import com.example.SeetLeft.Tool.JwtUtils;
import com.example.SeetLeft.Tool.FileUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

//HandlerInterceptor：Spring MVC框架提供的拦截器接口
@Slf4j
@Component
public class LoginLinterCeptor implements HandlerInterceptor {

    @Resource
    private FileUtils T;

    @Resource
    private JwtUtils jwtUtils;

    /**
     * 拦截器：带有拦截器的接口被请求访问时，会先经过拦截器，再经过控制器，最后经过视图解析器，返回给前端
     *
     * @param request  接收前端请求对象
     * @param response 响应前端请求对象
     * @param handler  处理器对象
     * @return true：放行，false：拦截
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        //从请求头中获取 Authorization 字段的值，通常用于传递 JWT Token 或其他认证凭证
        String token = request.getHeader("Authorization");

        //获取请求头中的 Accept 字段，表示客户端期望接收的响应内容类型（如 application/json）
        String accept = request.getHeader("Accept");

        log.info("请求头中的token：{}", token);
        log.info("请求头中的Accept：{}", accept);
        if (token != null) {

            //判断token实际的JWT令牌字符串的前缀是否为 "Bearer "
            if (token.startsWith("Bearer ")) {

                //去掉token前7个字符也就是"Bearer "，提取剩下的JWT令牌字符串
                String tokens = token.substring(7);
                log.info("请求发送成功，正在验证令牌合理性");
                try {
                    //开始对提取出的JWT令牌进行解析和验证
                    jwtUtils.parseToken(tokens);
                    log.info("token验证成功");
                } catch (ExpiredJwtException e) {

                    //说明令牌已过期，设置HTTP状态码为401并返回"Token expired"消息
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token expired");
                } catch (JwtException e) {

                    //说明令牌无效，设置HTTP状态码为401并返回"Invalid token"消息
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Invalid token");
                }
            }
            throw new Exception("无效的Authorization头格式，需要Bearer token");
        }
        log.info("token为空");
        return false;
    }
}