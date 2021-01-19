package com.m.edmsbackend.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.m.edmsbackend.exception.AuthorizationException;
import com.m.edmsbackend.exception.ExpiredException;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    public static final String JWT_SECRET = "fsadofh24ouru0923ru09wfjlksdf";
    public static final long JWT_TTL = 60 * 24 * 60 * 60 * 1000L;
    public static final SignatureAlgorithm alg = SignatureAlgorithm.HS256;
    //生成签名的时候使用的秘钥secret
    static byte[] encodedKey = Base64.getEncoder().encode(JWT_SECRET.getBytes());
    static SecretKey secretKey = new SecretKeySpec(encodedKey, alg.getJcaName());
    // static SecretKey secretKey = Keys.secretKeyFor(alg);

    /**
     * 创建Jwt
     * @param uuid
     * @return
     */
    public static String createJWT(String uuid, Integer type ) {
        //生成jwt时间
        long currentTime = System.currentTimeMillis();
        Date current = new Date(currentTime);
        // 设置jwt头部
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        // 创建payload的私有声明
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("userType", type);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setHeader(map)
                //设置jti(JWT ID)：是JWT的唯一标识
                .setId(uuid)
                //设置过期时间
                .setExpiration(new Date(currentTime + JWT_TTL))
                //iat: jwt的签发时间
                .setIssuedAt(current)
//                //代表这个JWT的主体，即它的所有人
//                .setSubject(mobile)
                .signWith(secretKey);
        return jwtBuilder.compact();
    }

    /**
     * Token的解析
     *
     * @param token
     * @return
     */
    public static Claims parseJWT(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody();
            return claims;
        }catch (ExpiredJwtException ex){
            throw new ExpiredException();
        }
        catch (Exception ex){
            throw new AuthorizationException("token 解析失败");
        }
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static boolean checkToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody();
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    /**
     * 解析jwt中的uuid
     * @param token
     * @return
     */
    public static String  parseJwtToUuid(String token) {
        try {
            String uuid = Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody().getId();
            return uuid;
        }catch (Exception ex){
            throw new AuthorizationException("uuid token解析失败");
        }
    }

    /**
     * 解析Jwt中的userType
     * @param token
     * @return
     */
    public static Integer parseJwtToUserType(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody();
            Object Type = claims.get("userType");
            Integer userType = Integer.decode(Type.toString());
            return userType;
        }catch (Exception ex){
            throw new AuthorizationException("userType token解析失败");
        }
    }
}
