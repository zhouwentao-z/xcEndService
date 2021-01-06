package com.xuecheng.govern.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author 周文韬
 * @create 2020-12-19 11:57:35
 * @desc ...
 */
@SpringBootApplication
@EnableEurekaServer         //表示这是一个Eureka工程
public class GovernCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(GovernCenterApplication.class,args);
    }
}
