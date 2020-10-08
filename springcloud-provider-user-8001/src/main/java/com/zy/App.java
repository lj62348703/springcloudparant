package com.zy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.zy.repository")//扫描data-jpa组件
@EntityScan("com.zy.entity")//扫描实体类
public class App {
    public static void main(String[] args) {
        System.out.println("启动提供端...8001.......");
        SpringApplication.run(App.class, args);

        System.out.println("提供端....8001......启动成功");
    }
}
