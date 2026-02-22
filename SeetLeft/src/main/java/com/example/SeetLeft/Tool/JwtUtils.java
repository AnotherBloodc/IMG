package com.example.SeetLeft.Tool;

import com.example.SeetLeft.Config.JwtProperties;
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

//JWT工具类
@Component
@Slf4j
public class JwtUtils {

    //token有效期,单位：毫秒
    private static final long expire = 60 * 1000 * 2;

    //32位密钥
    private static SecretKey key;

    public JwtUtils(JwtProperties jwtProperties) {
        // 使用配置文件中的密钥
        key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
        log.info("JWT密钥已初始化");
    }

    //生成token
    public static String generateToken(String username) {

        //创建时间对象
        Date now = new Date();

        //设置token的失效时间，用now.getTime()获取当前时间戳，设置置未来何时到期
        Date expireDate = new Date(now.getTime() + expire);

        //调用builder()方法，生成token
        return Jwts.builder()
                //.setHeaderParam("typ", "JWT")//设置token类型
                .subject(username)//设置token的载荷，主体
                .issuedAt(now)//设置token的生效时间
                .expiration(expireDate)//设置token过期时间
                .signWith(key)//设置签名算法和密钥
                .compact();//生成token
    }

    //解析token
    public static Claims getClaimsByToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()//调用parser()方法，生成Claims对象
                    .verifyWith(key)//设置签名算法和密钥，查看token有没有被篡改
                    .build()//获取token并解析
                    .parseSignedClaims(token);//提供一个字符串进行解析得到一个Jws类型的对象claims
            return claims.getPayload();
        } catch (JwtException a) {
            return null;
        }
    }
}