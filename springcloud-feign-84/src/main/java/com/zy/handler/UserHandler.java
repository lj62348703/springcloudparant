package com.zy.handler;


import com.zy.entity.Userinfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FeignClient(value = "eureka-provider",fallbackFactory = UserHandlerFallback.class)
public interface UserHandler {

    //http://localhost:84/user/get/1
    @RequestMapping("/user/get/{id}")
    public Userinfo findById(@PathVariable("id") Integer userid);


    //http://localhost:84/user/list
    @SuppressWarnings("unchecked")
    @RequestMapping("/user/list")
    public List<Userinfo> findAll() ;

    //http://localhost:84/user/add?userName=%E5%B0%8F%E7%BA%A2&userPwd=123
    @RequestMapping("/user/add")
    public boolean save(Userinfo userinfo);
}
