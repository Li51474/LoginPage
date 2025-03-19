package com.hati.aiformat.service;

import com.hati.aiformat.pojo.User;

public interface UserService {
    //注册
    void register(User user);
    //登录
    User login(User user);
    //根据用户名查询用户信息（内部使用）
    User findByUsername(String username);
    //根据ID查询用户信息（对外接口使用）
    User findById(Integer id);

    void update(User user);

    void updatePwd(User user);
}