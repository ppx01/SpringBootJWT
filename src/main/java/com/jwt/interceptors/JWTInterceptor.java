package com.jwt.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.util.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jingdeyue
 * @date 2023/1/31 18:03
 */
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        Map<String, Object> map = new HashMap<>();
        try {
            JWTUtils.verify(token);
            return true;
        }catch (SignatureVerificationException e) {
            map.put("msg", "签名不一致");
            e.printStackTrace();
        }catch (TokenExpiredException e){
            map.put("msg", "令牌过期");
            e.printStackTrace();
        } catch (AlgorithmMismatchException e){
            map.put("msg", "算法不匹配");
            e.printStackTrace();
        }catch (InvalidClaimException e){
            map.put("msg", "负载异常");
            e.printStackTrace();
        }

        map.put("state", false);
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=utf8");
        response.getWriter().println(json);
        return false;
    }
}
