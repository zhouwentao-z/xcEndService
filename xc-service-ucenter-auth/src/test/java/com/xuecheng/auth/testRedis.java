package com.xuecheng.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 周文韬
 * @create 2021-01-08 19:12:00
 * @desc ...
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class testRedis {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void testredis(){
        //定义key
        String key = "user_token:9a39672c-40ba-4de2-a02d-102a45d7ed58";
        //定义value
        Map<String, String> value = new HashMap<>();
        value.put("jwt","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOm51bGwsInVzZXJwaWMiOm51bGwsInVzZXJfbmFtZSI6IlhjV2ViQXBwIiwic2NvcGUiOlsiYXBwIl0sIm5hbWUiOm51bGwsInV0eXBlIjpudWxsLCJpZCI6bnVsbCwiZXhwIjoxNjEwMTMxNzQ0LCJqdGkiOiI5YTM5NjcyYy00MGJhLTRkZTItYTAyZC0xMDJhNDVkN2VkNTgiLCJjbGllbnRfaWQiOiJYY1dlYkFwcCJ9.T8CP7xbNOZFSqiGwS8Gom8iHKiu0NxtEXcFXNNBmDHlfF8w4Lx-qIJie-EnE4-5aeoRJfZA8LH85y3IfZswg-avFIZXBVcdncuwOmyKZ48oBGM91tBRCDdiAI5k7eiLrT46_sVNG7TQDjjINO59wDea0YdZM2-VjhyMzhyr9QYnj5NgWSKTzdLF476CmCX2W1KGDHLYXBygya6meoIFcWX694bCVTkXOXT64rogbGFc5CwZyM85eR0BZsP--bArdBqDQM5qDK4e8UEgpxl8tbuJeZzN4iJeDaYwsCtqir3hLud7-9n2EGqqAfSWCy3axMIrw4xdx-vNhD6Wr5cX-zA");
        value.put("refresh_token", "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOm51bGwsInVzZXJwaWMiOm51bGwsInVzZXJfbmFtZSI6IlhjV2ViQXBwIiwic2NvcGUiOlsiYXBwIl0sImF0aSI6IjlhMzk2NzJjLTQwYmEtNGRlMi1hMDJkLTEwMmE0NWQ3ZWQ1OCIsIm5hbWUiOm51bGwsInV0eXBlIjpudWxsLCJpZCI6bnVsbCwiZXhwIjoxNjEwMTMxNzQ0LCJqdGkiOiI5Y2E0ZDg5OS02OWViLTQ5MTItYTFjMC01YmY0ZmE3Zjk2OTkiLCJjbGllbnRfaWQiOiJYY1dlYkFwcCJ9.Q9frA1BShBeE5-eDjPHphetgttoJR72jOTrp8Ee3H9m84jFBXQ2-1y_SMDd23ckY0IEB4eSFeIWi-fmVQu-YZl0MMTGv9YzY9xn_YR9Z6ZLNENGS8UHHtn6v49kJTJUrme1JLUxedIeaFN4vZwXhPkvfFkd7Q7-LtqYkyRVYstUff2dkQoHrw5ohPyoJdYDNhIly5TTkzugVNclbSjFvn-aFEaR9jmvW4cJY8VC1sMundDBLPcs7OfdpPH_1Ibsqt8uGClLRRbWQaE2RRVzJDzFBhodSZ4IcMFs3WeMp2XS6rqF0n0CKkcHEoqmpQBN3MuMfRMAdXYTdwIu-kxzNgQ");

        String jsonstring = JSON.toJSONString(value);
        //存储数据
        stringRedisTemplate.boundValueOps(key).set(jsonstring,30, TimeUnit.SECONDS);

        //获取数据
        String values = stringRedisTemplate.opsForValue().get(key);
        System.out.println(values);

    }
}
