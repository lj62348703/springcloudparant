package com.zy.handler;

import com.zy.entity.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class UserHandler {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    //http://localhost:81/user/get/1
    @RequestMapping("/user/get/{id}")
    public Userinfo findById(@PathVariable("id") Integer userid) {
        System.out.println("服务消费端------------findById");
        //根据服务ID获取实例
        List<ServiceInstance> userService = discoveryClient.getInstances("eureka-provider");
        //从实例中取出IP和端口
        ServiceInstance instance = userService.get(0);
        String host = instance.getHost();
        int port = instance.getPort();
        return restTemplate.getForObject(
                "http://"+host+":"+port+"/user/get/"+ userid, Userinfo.class);
    }

    //http://localhost:81/user/list
    @SuppressWarnings("unchecked")
    @RequestMapping("/user/list")
    public List<Userinfo> findAll() {
        System.out.println("服务消费端------------findAll");
        //根据服务ID获取实例
        List<ServiceInstance> service = discoveryClient.getInstances("eureka-provider");
        //从实例中取出IP和端口
        ServiceInstance instance = service.get(0);
        String host = instance.getHost();
        int port = instance.getPort();
        System.out.println("http://"+host+":"+port+"/user/list");
        return restTemplate.getForObject(
                "http://"+host+":"+port+"/user/list", List.class);
    }

    //http://localhost:81/user/add?userName=%E5%B0%8F%E7%BA%A2&userPwd=123
    @RequestMapping("/user/add")
    public boolean save(Userinfo userinfo) {
        System.out.println("服务消费端------------save");
        //根据服务ID获取实例
        List<ServiceInstance> userService = discoveryClient.getInstances("eureka-provider");
        //从实例中取出IP和端口
        ServiceInstance instance = userService.get(0);
        String host = instance.getHost();
        int port = instance.getPort();
        return restTemplate.postForObject(
                "http://"+host+":"+port+"/user/add",userinfo, Boolean.class);
    }
}
