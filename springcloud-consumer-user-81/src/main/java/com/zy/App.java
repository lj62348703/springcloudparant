package com.zy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        System.out.println("启动消费端...81.......");
        SpringApplication.run(App.class, args);

        System.out.println("消费端....81......启动成功");
    }

    @Bean//加载RestTemplate
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}