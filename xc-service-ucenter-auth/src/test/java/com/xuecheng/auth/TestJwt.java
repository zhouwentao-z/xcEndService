package com.xuecheng.auth;

import com.alibaba.fastjson.JSON;
import jdk.nashorn.internal.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 周文韬
 * @create 2021-01-08 17:01:19
 * @desc ...
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestJwt {

    @Test
    public void testJwtkey(){
        //密钥库文件
        String keystore = "xc.keystore";
        //密钥库密码
        String keystore_password = "xuechengkeystore";
         ClassPathResource classPathResource= new ClassPathResource(keystore);

        //密钥别名
        String alias = "xckey";
        //密钥访问密码
        String key_password = "xuecheng";
        //密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource,keystore_password.toCharArray());
        //密钥对（公钥和私钥）
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, key_password.toCharArray());
        //获取私钥
        RSAPrivateKey rsaPrivateKey =(RSAPrivateKey) keyPair.getPrivate();
        //jwt令牌的内容
        Map<String, String> map = new HashMap<>();
        map.put("name","heima");
        String mapString = JSON.toJSONString(map);
        //生成jwt令牌
        Jwt jwt = JwtHelper.encode(mapString, new RsaSigner(rsaPrivateKey));
        String jwtEncoded = jwt.getEncoded();
        System.out.println(jwtEncoded);
    }


    //校验jwt令牌
    @Test
    public  void testVerify(){
        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----GMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnASXh9oSvLRLxk901HANYM6KcYMzX8vFPnH/To2R+SrUVw1O9rEX6m1+rIaMzrEKPm12qPjVq3HMXDbRdUaJEXsB7NgGrAhepYAdJnYMizdltLdGsbfyjITUCOvzZ/QgM1M4INPMD+Ce859xse06jnOkCUzinZmasxrmgNV3Db1GtpyHIiGVUY0lSO1Frr9m5dpemylaT0BV3UwTQWVW9ljm6yR3dBncOdDENumT5tGbaDVyClV0FEB1XdSKd7VjiDCDbUAUbDTG1fm3K9sx7kO1uMGElbXLgMfboJ963HEJcU01km7BmFntqI5liyKheX+HBUCD4zbYNPw236U+7QIDAQAB-----END PUBLIC KEY-----";
        //jwt令牌
        String jwtS= "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiaGVpbWEifQ.AhJTBlHFzkRBmz9BHHLg-za89oUmXQoXtyt8_QCHH_KXyqNNBBnPuto5sABQafu1axl3cNLxQQuIama2RlMjPavCpr7L5zR6yi9ger2X4aB1W6dDy-OZ0AbO_Hfvyvi9qeMYTkeTZOvUxFsnf4KFA9qEjweaQM8nED7tWEgbQOBA6MqUL4zfBO4-Vn3IdI3fRrXyV8-OKrOD0UMcfEPu5m7Eb2S66VTsRiE7kTIEL2bVVl38er59RxK79YAuxEBMyz28zBhLAKx_KXmpTVXzf16rEClxVtFipcUuxNc4z_K3FRMEMSVh7Vfj3hmdnB2SwxPSl3Uj2NeJuy_LIMyNPQ";
        Jwt jwt = JwtHelper.decodeAndVerify(jwtS, new RsaVerifier(publickey));
        //拿到jwt令牌中的内容
        String claims = jwt.getClaims();
        System.out.println(jwt);


    }
}
