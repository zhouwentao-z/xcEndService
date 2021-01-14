package com.xuecheng.auth;

import com.xuecheng.framework.client.XcServiceList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * @author 周文韬
 * @create 2021-01-08 21:54:18
 * @desc ...
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class testClient {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Test
    public void testClient(){

        //从eureka中获取认证服务的地址 （因为spring security在认证服务中）
        //从eureka中获取认证服务的一个实例地址
        ServiceInstance serviceInstance = loadBalancerClient.choose(XcServiceList.XC_SERVICE_UCENTER_AUTH);
        //此地址就是http://ip:port
        URI uri = serviceInstance.getUri();
        //令牌申请地址 http://localhost:40400/auth/oauth/token
        String authUrl = uri+"/auth/oauth/token";
        //定义header
        LinkedMultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        String basic = getBasic("XcWebApp", "XcWebApp");
        header.add("Authorization",basic);

        //定义Body
        LinkedMultiValueMap<String,String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username","itcast");
        body.add("password","1231");
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
        ResponseEntity<Map> exchange = restTemplate.exchange(authUrl, HttpMethod.POST, multiValueMapHttpEntity, Map.class);

        //申请令牌信息
        Map bodymap = exchange.getBody();
        System.out.println(bodymap);

    }
    //获取httpbasic的串
    private String getBasic(String clientId,String clientSecret){
        String  s = clientId+":"+clientSecret;
        //将串进行base64编码
        byte[] encode = Base64Utils.encode(s.getBytes());
        return "Basic "+ new String(encode);
    }

    @Test
    public void testPasswordEncoder(){
        String  password = "123456";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        for (int i=0;i<10;i++){
            String encode = bCryptPasswordEncoder.encode(password);
            System.out.println(encode);

            //校验
            boolean matches = bCryptPasswordEncoder.matches(password,encode);
            System.out.println(matches);
        }
    }

}
