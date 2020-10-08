package com.zy.service.impl;

import com.zy.entity.Userinfo;
import com.zy.repository.UserRepository;
import com.zy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Userinfo findById(Integer userid) {
        return userRepository.findById(userid).get();
    }

    @Override
    public List<Userinfo> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(Userinfo userinfo) {
        userRepository.save(userinfo);
    }

    @Override
    public Userinfo findByUserName(String  userName) {
        return userRepository.findByUserName(userName);
    }
}
