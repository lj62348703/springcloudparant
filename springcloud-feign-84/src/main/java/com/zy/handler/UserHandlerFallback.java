package com.zy.handler;

import com.zy.entity.Userinfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserHandlerFallback implements FallbackFactory<UserHandler> {
    public UserHandler create(Throwable cause) {
        // TODO Auto-generated method stub
        return new UserHandler() {
            @Override
            public Userinfo findById(Integer userid) {
                return new Userinfo().setUserName("错了");
            }

            @Override
            public List<Userinfo> findAll() {
                return null;
            }

            @Override
            public boolean save(Userinfo userinfo) {
                return false;
            }

        };
    }

}
