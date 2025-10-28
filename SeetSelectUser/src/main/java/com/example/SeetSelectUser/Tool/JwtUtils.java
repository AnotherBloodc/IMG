package com.example.SeetSelectUser.Tool;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtils {
    private static final long expire = 60 * 60;
    private static final SecretKey key = Jwts.SIG.HS512.key().build();

    public static String generateToken(String username) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + 1000 * expire);
        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(key)
                .compact();
    }

    public static Claims getClaimsByToken(String token) {
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
        return claims.getPayload();
    }
}
