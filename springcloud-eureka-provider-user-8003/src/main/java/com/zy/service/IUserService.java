package com.zy.service;

import com.zy.entity.Userinfo;

import java.util.List;

public interface IUserService {
    Userinfo findById(Integer userid);

    List<Userinfo> findAll();

    void save(Userinfo userinfo);

    public Userinfo findByUserName(String userName);
}
