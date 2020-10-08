package com.zy.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Userinfo implements Serializable {
    private Integer userId;

    private String userName;
    private String userPwd;
    private String userNick;
}
