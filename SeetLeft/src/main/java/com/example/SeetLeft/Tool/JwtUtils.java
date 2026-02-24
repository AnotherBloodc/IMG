package com.example.SeetLeft.Tool;

import com.example.SeetLeft.Config.JwtProperties;
import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Enums.LogH;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//JWT工具类
@Component
@Slf4j
public class JwtUtils {

    //token有效期,单位：小时
    private static final long EXPIRATION_TIME = 2 * 60 * 60 * 1000;

    //32位密钥
    private static SecretKey key;

    //密钥初始化
    public JwtUtils(JwtProperties jwtProperties) {
        // 使用配置文件中的密钥
        key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
        log.info("JWT密钥已初始化");
    }

    //生成token
    public String generateToken(User user) {
        log.info(LogH.GO);
        log.info("执行生成token");
        //创建时间对象
        Date now = new Date();

        //设置token的失效时间，用now.getTime()获取当前时间，设置未来何时到期
        Date expireDate = new Date(now.getTime() + EXPIRATION_TIME);

        //创建token的载荷
        Map<String, Object> customClaims = new HashMap<>();
        customClaims.put("username", user.getUsername()); // 保留用户名
        customClaims.put("userId", user.getId()); // 新增用户ID（非敏感，更唯一）

        log.info("用户信息为：" + user);
        log.info("过期时间为：" + expireDate);
        log.info(LogH.EXIT);
        //调用builder()方法，生成token
        return Jwts.builder()
                .claims(customClaims)//设置token的载荷，主体
                .subject(user.getId().toString())//此token的唯一标识，可以用户ID为标识，唯一且不敏感
                .issuedAt(now)//设置token的生效时间
                .expiration(expireDate)//设置token过期时间
                .signWith(key, Jwts.SIG.HS256)//显式指定HS256算法（和密钥匹配），避免静默降级为none
                .compact();//生成token
    }

    //解析token
    public Claims parseToken(String token) {
        log.info(LogH.GO);
        log.info("开始解析token：" + token);
        try {
            Jws<Claims> claims = Jwts.parser()//调用parser()方法，生成Claims对象
                    .verifyWith(key)//设置签名算法和密钥，查看token有没有被篡改
                    .build()//获取token并解析
                    .parseSignedClaims(token);//提供token进行解析得到一个Jws类型的对象claims
            log.info("解析成功，结果为：" + claims);
            log.info(LogH.EXIT);
            return claims.getPayload();
        } catch (JwtException a) {
            log.error("解析Token失败", a);
            log.info(LogH.EXIT);
            throw new RuntimeException("无效的Token", a);
        }
    }
}