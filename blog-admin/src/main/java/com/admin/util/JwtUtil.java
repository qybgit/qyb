package com.admin.util;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * jwt生成工具
 */
public class JwtUtil {

    private static String  signToken="asdfasfasdfdsadcsadfsafsddasfdsadfasfdsadasd";//签名
    private static int time=100*60*60*24;//时间
    //
    public static String createToken(long userId){
        JwtBuilder jwtBuilder= Jwts.builder();
        String jwtToken=jwtBuilder.
                setHeaderParam("typ","jwt")
                .setHeaderParam("alg","HS26")
                //header
                .claim("userId",userId)
                .setExpiration(new Date(System.currentTimeMillis()+time))//过期时间
                .signWith(SignatureAlgorithm.HS256,signToken)//签名算法
                .compact();
        return jwtToken;
    }
    public   static Map<String,Object> checkToken(String token){
        try {
            Jwt parse=Jwts.parser().setSigningKey(signToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
