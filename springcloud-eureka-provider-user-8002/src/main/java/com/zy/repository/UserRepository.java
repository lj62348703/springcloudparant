package com.zy.repository;

import com.zy.entity.Userinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<Userinfo, Integer>
        , JpaSpecificationExecutor<Userinfo> {
    Userinfo findByUserName(String userName);
}
