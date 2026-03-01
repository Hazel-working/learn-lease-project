package com.atguigu.lease.common.utils;

import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    private static long tokenExpiration = 60 * 60 * 1000L; //token过期时间：1小时；long类型需要末尾加L声明
    private static SecretKey tokenSignKey = Keys.hmacShaKeyFor("M0PKKI6pYGVWWfDZw90a0lTpGYX1d4AQ".getBytes());

    // 生成 createToken方法
    public static String createToken(Long userId, String username) {
        String jwt = Jwts.builder().
                setSubject("LOGIN_INFO").
                setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)).//设置过期时间
                claim("userId", userId).//添加自定义声明k-v
                claim("username", username).
                signWith(tokenSignKey, SignatureAlgorithm.HS256).//密钥签名
                compact();
        return jwt;
    }

    // 解析 parseToken方法：为所有受保护的接口增加校验JWT合法性的逻辑
    public static Claims parseToken(String token){

        if (token==null){ //校验token是否为空
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        }

        try{
            //parseBuilder()生成JwtParser对象, 调用setSigningKey方法设置密钥（与创建时密钥一致）
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(tokenSignKey).build();
            //parseClaimsJws()方法解析token字符串，返回Jws<Claims>对象  jws是有签名的jwt
            //getBody方法获取token中的有效载荷（payload）部分，即Claims对象 返回
            return jwtParser.parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            throw new LeaseException(ResultCodeEnum.TOKEN_EXPIRED); //过期异常
        }catch (JwtException e){
            throw new LeaseException(ResultCodeEnum.TOKEN_INVALID);//其他非法异常
        }
    }



    public static void main(String[] args) {
        System.out.println(createToken(2L, "user"));
    }


}