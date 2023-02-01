package com.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @author jingdeyue
 * @date 2023/1/31 18:04
 */
public class JWTUtils {

    public static final String SIGNATURE = "fudsifu98ewqurw90ur&^*()#(JI()WEPJBVDS";

    // 生成 token
    public static String getToken(Map<String, String> map){
        JWTCreator.Builder builder = JWT.create();
        map.forEach((key, value)->{
            builder.withClaim(key, value);
        });

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, 7);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(SIGNATURE));
    }


    /**
     * 验证 token 的合法性
     * @param token
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }

    /**
     * 获取 token 信息方法
     * @param token
     * @return
     */
    public static DecodedJWT getToken(String token){
        return JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }
}
