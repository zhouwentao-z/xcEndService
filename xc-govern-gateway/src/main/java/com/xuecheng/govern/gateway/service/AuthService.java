package com.xuecheng.govern.gateway.service;

import com.xuecheng.framework.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 周文韬
 * @create 2021-01-11 22:22:15
 * @desc ...
 */
@Service
public class AuthService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //从头取出jwt令牌
    public String getJwtFromHeader(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)){
            return null;
        }
        if (!authorization.startsWith("Bearer")){
            return  null;
        }
    //取到jwt令牌
        String jwt = authorization.substring(7);
        return jwt;
    }

    //从cookie获取token
    public String getTokenCookie(HttpServletRequest request){
        Map<String, String> map = CookieUtil.readCookie(request);
        String access_token = map.get("uid");
        if (StringUtils.isBlank(access_token)){
            return null;
        }
        return access_token;
    }
    //查询令牌的有效期
    public  long getExpire(String access_token){
        String key = "user_token:"+access_token;
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire;
    }
}
