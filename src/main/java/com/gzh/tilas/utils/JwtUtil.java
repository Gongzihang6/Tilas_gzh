// utils/JwtUtil.java
package com.gzh.tilas.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${Tilas.jwt.secret-key}") // 从配置文件读取密钥
    private String secretKey;

    @Value("${Tilas.jwt.expire-time}") // 从配置文件读取过期时间（毫秒）
    private long expireTime;

    /**
     * 生成JWT令牌
     * @param claims 载荷 (Payload)，包含要存储的用户信息，如 { "id": 1, "username": "admin" }
     * @return JWT字符串
     */
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims) // 设置自定义载荷
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expireTime)) // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, secretKey) // 设置签名算法和密钥
                .compact();
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT字符串
     * @return Claims对象，包含了载荷信息
     */
    public Claims parseToken(String jwt) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
