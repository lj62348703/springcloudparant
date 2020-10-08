package com.zy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient//让注册中心能够发现，扫描到该服务。
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        System.out.println("启动消费端...82.......");
        SpringApplication.run(App.class, args);

        System.out.println("消费端....82......启动成功");
    }

    @Bean//加载RestTemplate
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
