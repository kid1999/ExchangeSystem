package io.kid1999.esystem.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.kid1999.esystem.common.Constants.TOKEN_EXPIRE_DATE;
import static io.kid1999.esystem.common.Constants.TOKEN_SECRET;

/**
 * @author kid1999
 * @create 2020-10-01 11:59
 * @description token 工具类
 **/
@Deprecated
@Component
public class TokenUtil {

    /**
     * @desc   实现签名方法
     **/
    public String createToken (String userName,Long userId){
        JwtBuilder jwtBuilder = Jwts.builder()
                // 标识 jti
                .setId("123456")
                // 主体
                .setSubject("user")
                // 签发时间
                .setIssuedAt(new Date())
                // 失效时间
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_DATE))
                // 加密方式
                .signWith(SignatureAlgorithm.HS256,TOKEN_SECRET)
                // 负载 自定义内容
                .claim("role","user")
                .claim("userName",userName)
                .claim("userId",userId);
        return jwtBuilder.compact();
    }

    /**
     * @desc   验证token，通过返回true
     * @params [token]需要校验的串
     **/
    public boolean verify(String token){
        try {
            Claims claims = Jwts.parser()
                    // 盐
                    .setSigningKey(TOKEN_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(claims.getId());
            System.out.println(claims.getSubject());
            System.out.println(claims.getIssuedAt());
            System.out.println(claims.getExpiration());
            System.out.println(System.currentTimeMillis());
            System.out.println(claims.get("userName"));
            System.out.println(claims.get("userId"));
            return true;
        }catch (Exception e){
            return  false;
        }
    }


}