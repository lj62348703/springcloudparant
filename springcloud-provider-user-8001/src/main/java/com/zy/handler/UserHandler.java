package com.zy.handler;

import com.zy.entity.Userinfo;
import com.zy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserHandler {
    @Autowired
    private IUserService userServiceImpl;

    @RequestMapping("/user/get/{id}")
    public Userinfo findById(@PathVariable("id") Integer userid) {
        System.out.println("服务提供端------------findById");
        return userServiceImpl.findById(userid);
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
}