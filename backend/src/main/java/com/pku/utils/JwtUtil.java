package com.pku.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * 生成jwt
     * 使用Hs256算法, 私匙使用固定秘钥
     *
     * @param secretKey jwt秘钥
     * @param ttlMillis jwt过期时间(毫秒)
     * @param claims    设置的信息
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 签发时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 构建JWT
        return Jwts.builder()
                .claims(claims)
                .issuer("archester-250")  // 签发者
                .issuedAt(now)  // 签发时间
                .expiration(new Date(nowMillis + ttlMillis))  // 设置过期时间
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), Jwts.SIG.HS256)  // 设置签名算法及密钥
                .compact();  // 压缩并生成最终的JWT
    }

    /**
     * Token解密
     *
     * @param secretKey jwt秘钥 此秘钥一定要保留好在服务端, 不能暴露出去, 否则sign就可以被伪造, 如果对接多个客户端建议改造成多个
     * @param token     加密后的token
     */
    public static Jws<Claims> parseJWT(String secretKey, String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseSignedClaims(token);
    }

}
