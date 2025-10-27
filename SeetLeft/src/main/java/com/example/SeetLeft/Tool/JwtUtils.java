package com.example.SeetLeft.Tool;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtils {

    //token有效期,单位：毫秒
    private static final long expire = 60 * 60;

    //32位密钥
    private static final SecretKey key = Jwts.SIG.HS512.key().build();

    //生成token
    public static String generateToken(String username) {
        //创建时间对象
        Date now = new Date();
        //设置token的失效时间，用now.getTime()获取当前时间戳，加上1000 * expire:将毫秒转为秒来设置未来何时到期
        Date expireDate = new Date(now.getTime() + 1000 * expire);
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
        Jws<Claims> claims = Jwts.parser()//调用parser()方法，生成Claims对象
                .verifyWith(key)//设置签名算法和密钥，查看token有没有被篡改
                .build()//获取token并解析
                .parseSignedClaims(token);//提供一个字符串进行解析得到一个Jws类型的对象claims
        return claims.getPayload();
    }
}
