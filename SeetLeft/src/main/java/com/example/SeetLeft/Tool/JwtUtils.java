package com.example.SeetLeft.Tool;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Date;

//JWT工具类
@Slf4j
public class JwtUtils {

    //token有效期,单位：毫秒
    private static final long expire = 60 * 1000;

    //32位密钥
    private static final SecretKey key = Jwts.SIG.HS512.key().build();

    //日志对象
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    //从yaml文件中jwt.secret中读取数据
    private static String jwtSecret;


    //生成密钥对象,HS256
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    //生成token
    public static String generateToken(String username) {
        //创建时间对象
        Date now = new Date();

        //设置token的失效时间，用now.getTime()获取当前时间戳，设置置未来何时到期
        Date expireDate = new Date(now.getTime() + expire);

        //调用builder()方法，生成token
        String token = Jwts.builder()
                //.setHeaderParam("typ", "JWT")//设置token类型
                .subject(username)//设置token的载荷，主体
                .issuedAt(now)//设置token的生效时间
                .expiration(expireDate)//设置token过期时间
                .signWith(key)//设置签名算法和密钥
                .compact();//生成token
        log.info("生成token成功:{}", token);
        return token;
    }

    //解析token
    public static Claims getClaimsByToken(String token) {
        Jws<Claims> claims = Jwts.parser()//调用parser()方法，生成Claims对象
                .verifyWith(key)//设置签名算法和密钥，查看token有没有被篡改
                .build()//获取token并解析
                .parseSignedClaims(token);//提供一个字符串进行解析得到一个Jws类型的对象claims
        log.info("解析token成功:{}", claims);
        return claims.getPayload();
    }

    //验证令牌有效期
    public static boolean validateToken(String token) {
        try {
            logger.error("token验证成功{}", getClaimsByToken(token));
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("token验证失败", e);
        }
        return false;
    }
}