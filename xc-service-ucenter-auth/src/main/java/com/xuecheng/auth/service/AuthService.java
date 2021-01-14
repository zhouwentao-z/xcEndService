package com.xuecheng.auth.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.client.XcServiceList;
import com.xuecheng.framework.domain.ucenter.ext.AuthToken;
import com.xuecheng.framework.domain.ucenter.response.AuthCode;
import com.xuecheng.framework.exception.ExceptionCast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 周文韬
 * @create 2021-01-09 10:38:56
 * @desc ...
 */
@Service
public class AuthService {

    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //用户认证申请令牌
    public AuthToken login(String username, String password, String cilentId, String clientSecret) {
        //请求spring security申请令牌
        AuthToken authToken = this.applyToken(username, password, cilentId, clientSecret);
        if (authToken == null){
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FALL);
        }
        //用户身份令牌
        String access_token = authToken.getAccess_token();
        String jsonString = JSON.toJSONString(authToken);
        //将令牌存储到redis
        boolean token = this.saveRedis(access_token, jsonString, tokenValiditySeconds);
        if (!token){
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_TOKEN_SAVEFALL);
        }
        return authToken;

    }

    /**
     * 将令牌存储到redis
     * @param access_token  用户身份令牌
     * @param content   token对象的内容
     * @param ttl   过期时间
     * @return
     */
    //保存token
    private boolean saveRedis(String access_token,String content,long ttl){
        String key = "user_token:"+access_token;
        stringRedisTemplate.boundValueOps(key).set(content,ttl, TimeUnit.SECONDS);
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        return  true;
    }
    //删除token
    public boolean deleteToken(String access_token){
        String key = "user_token:"+access_token;
        stringRedisTemplate.delete(key);
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        return  expire<0;
    }
    public AuthToken getUserToken(String token){
        String  key = "user_token:"+token;
        //从redis中取到令牌信息
        String value = stringRedisTemplate.opsForValue().get(key);
         //转成对象
        try{
            AuthToken authToken = JSON.parseObject(value, AuthToken.class);
            return  authToken;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
    //申请令牌
    private AuthToken applyToken(String username, String password, String cilentId, String clientSecret){
        //从eureka中获取认证服务的地址 （因为spring security在认证服务中）
        //从eureka中获取认证服务的一个实例地址
        ServiceInstance serviceInstance = loadBalancerClient.choose(XcServiceList.XC_SERVICE_UCENTER_AUTH);
        //此地址就是http://ip:port
        URI uri = serviceInstance.getUri();
        //令牌申请地址 http://localhost:40400/auth/oauth/token
        String authUrl = uri+"/auth/oauth/token";
        //定义header
        LinkedMultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        String basic = getBasic(cilentId, clientSecret);
        header.add("Authorization",basic);

        //定义Body
        LinkedMultiValueMap<String,String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username",username);
        body.add("password",password);
        HttpEntity<MultiValueMap<String, String>> multiValueMapHttpEntity = new HttpEntity<>(body, header);
        //设置restTemplate远程调用的时候，对400，401不报错，正常返回数据
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode()!=400 && response.getRawStatusCode()!=401){
                    super.handleError(response);
                }
            }
        });
        //申请令牌信息
        ResponseEntity<Map> exchange = restTemplate.exchange(authUrl, HttpMethod.POST, multiValueMapHttpEntity, Map.class);
        Map bodymap = exchange.getBody();
        if (bodymap == null || bodymap.get("access_token")==null || bodymap.get("refresh_token") ==null || bodymap.get("jti")==null){
            //解析spring security返回的错误信息
            if (bodymap != null && bodymap.get("error_description") != null){
              String error_description = (String) bodymap.get("error_description");
              if (error_description.indexOf("UserDetailsService returned null")>=0){
                  ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
              }else if(error_description.indexOf("坏的凭证")>=0){
                  ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
              }
            }
            return  null;
        }
        AuthToken authToken = new AuthToken();
        authToken.setAccess_token((String) bodymap.get("jti"));
        authToken.setRefresh_token((String) bodymap.get("refresh_token"));
        authToken.setJwt_token((String) bodymap.get("access_token"));
        return  authToken;
    }
    //获取httpbasic的串
    private String getBasic(String clientId,String clientSecret){
        String  s = clientId+":"+clientSecret;
        //将串进行base64编码
        byte[] encode = Base64Utils.encode(s.getBytes());
        return "Basic "+ new String(encode);
    }
}
