package com.zy.handler;

import com.zy.entity.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class UserHandler {
    @Autowired
    private RestTemplate restTemplate;

    //http://localhost:81/user/get/1
    @RequestMapping("/user/get/{id}")
    public Userinfo findById(@PathVariable("id") Integer userid) {
        System.out.println("服务消费端------------findById");
        return restTemplate.getForObject(
                "http://localhost:8001/user/get/"+ userid, Userinfo.class);
    }
    //http://localhost:81/user/list
    @SuppressWarnings("unchecked")
    @RequestMapping("/user/list")
    public List<Userinfo> findAll() {
        System.out.println("服务消费端------------findAll");
        return restTemplate.getForObject(
                "http://localhost:8001/user/list", List.class);
    }
    //http://localhost:81/user/add?userName=%E5%B0%8F%E7%BA%A2&userPwd=123
    @RequestMapping("/user/add")
    public boolean save(Userinfo userinfo) {
        System.out.println("服务消费端------------save");
        return restTemplate.postForObject(
                "http://localhost:8001/user/add",userinfo, Boolean.class);
    }
}
