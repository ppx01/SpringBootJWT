package com.jwt.controller;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jwt.entity.User;
import com.jwt.service.UserService;
import com.jwt.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jingdeyue
 * @date 2023/1/31 18:02
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/login")
    public Map<String, Object> login(User user) {

        log.info("用户名称 : [{}]", user.getName());
        log.info("用户密码 : [{}]", user.getPassword());
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            User login = userService.login(user);

            HashMap<String, String> payload = new HashMap<>();
            payload.put("id", login.getId());
            payload.put("name", login.getName());
            payload.put("password", login.getPassword());
            String token = JWTUtils.getToken(payload);
            hashMap.put("state", true);
            hashMap.put("msg", "认证成功");
            hashMap.put("token", token);
        } catch (Exception e) {
            hashMap.put("state", false);
            hashMap.put("msg", e.getMessage());
        }
        return hashMap;
    }

    // 第一版，直接在登录的时候进行验证
    @PostMapping("/user/test0")
    public Map<String, Object> test(String token) {
        Map<String, Object> map = new HashMap<>();
        log.info("当前 token 为 {}", token);

        try {
            DecodedJWT verify = JWTUtils.getToken(token);
            map.put("state", true);
            map.put("msg", "请求成功！");
            return map;
        } catch (SignatureVerificationException e) {
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
        return map;
    }


    // 第二版，在登录完成之后，无需验证，但是每次请求的时候都会带着 token, token 在拦截器中进行处理
    // 在注册完成之后，前端携带token 时要放在 token 中，后端从 request 中的请求头中获取
    @PostMapping("/user/test")
    public Map<String, Object> test(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.getToken(token);

        log.info("用户 id : " + verify.getClaim("id").asString());
        log.info("用户 name : " + verify.getClaim("name").asString());
        log.info("用户 password : " + verify.getClaim("password").asString());

        log.info("过期时间 : " + verify.getExpiresAt());

        map.put("msg", "认证成功");
        map.put("state", true);
        return map;
    }
}
