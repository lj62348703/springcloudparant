package com.zy.handler;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.zy.entity.Userinfo;
import com.zy.service.IUserService;
import com.zy.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class UserHandler {
    @Autowired
    private IUserService userServiceImpl;

    @RequestMapping("/user/get/{id}")
    @HystrixCommand(fallbackMethod = "findByIdFallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
    })
    public Userinfo findById(@PathVariable("id") Integer userid) throws Exception {
        System.out.println("服务提供端------------findById");
        if(userid == 0){
            throw new Exception("用户未找到");
        }
        return userServiceImpl.findById(userid);
    }

    public Userinfo findByIdFallback(@PathVariable("id") Integer userid) {
        System.out.println("服务提供端------------findById");
        Userinfo info = new Userinfo();
        info.setUserName("用户未找到");
        return info;
    }

    @RequestMapping("/user/list")
    public List<Userinfo> findAll() {
        System.out.println("服务提供端------------findAll");
        return userServiceImpl.findAll();
    }

    @RequestMapping("/user/add")
    public boolean save(@RequestBody Userinfo userinfo) {
        System.out.println("服务提供端------------save");
        try{
            userServiceImpl.save(userinfo);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @RequestMapping("/user/login/{userName}/{userPwd}")
    public String login(@PathVariable String userName,@PathVariable String userPwd) {
        System.out.println("服务提供端------------save");
        Userinfo userinfo = userServiceImpl.findByUserName(userName);
        if(userinfo == null){
            return "用户名不正确";
        }

        if(userPwd == null || !userPwd.equals(userinfo.getUserPwd())){
            return "密码错";
        }
        String token = TokenUtil.sign(userinfo);
        return token;
    }

    @RequestMapping("/user/test")
    public String verify(String token){
        int i = 1/0;
        return "test";
    }
}