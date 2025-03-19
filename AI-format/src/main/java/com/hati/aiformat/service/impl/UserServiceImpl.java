package com.hati.aiformat.service.impl;

import com.hati.aiformat.mapper.UserMapper;
import com.hati.aiformat.pojo.User;
import com.hati.aiformat.service.UserService;
import com.hati.aiformat.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        //1. 检查用户名是否已存在
        User u = userMapper.findByUsername(user.getUsername());
        if(u != null) {
            throw new RuntimeException("用户名已被占用");
        }

        //2. 对密码进行加密
        user.setPassword(Md5Util.encode(user.getPassword()));
        
        //3. 添加用户
        userMapper.add(user);
    }

    @Override
    public User login(User user) {
        //1. 根据用户名查询用户
        User loginUser = userMapper.findByUsername(user.getUsername());
        if(loginUser == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        //2. 判断密码是否正确
        if(!loginUser.getPassword().equals(Md5Util.encode(user.getPassword()))) {
            throw new RuntimeException("用户名或密码错误");
        }

        //3. 返回用户信息
        return loginUser;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User findById(Integer id) {
        //根据ID查询用户
        User user = userMapper.findById(id);
        //判断用户是否存在
        if(user == null) {
            throw new RuntimeException("用户不存在");
        }
        return user;
    }

    @Override
    public void update(User user) {
        //1. 检查用户是否存在
        User dbUser = userMapper.findById(user.getId());
        if(dbUser == null) {
            throw new RuntimeException("用户不存在");
        }

        //2. 设置更新时间
        user.setUpdateTime(LocalDateTime.now());
        
        //3. 更新用户信息
        userMapper.update(user);
    }

    @Override
    public void updatePwd(User user) {
        //1. 检查用户是否存在
        User dbUser = userMapper.findById(user.getId());
        if(dbUser == null) {
            throw new RuntimeException("用户不存在");
        }

        //2. 判断原密码是否正确
        if(!dbUser.getPassword().equals(Md5Util.encode(user.getOldPwd()))) {
            throw new RuntimeException("原密码错误");
        }

        //3. 判断新密码和确认密码是否一致
        if(!user.getNewPwd().equals(user.getRePwd())) {
            throw new RuntimeException("两次输入的密码不一致");
        }

        //4. 设置新密码和更新时间
        user.setPassword(Md5Util.encode(user.getNewPwd()));
        user.setUpdateTime(LocalDateTime.now());
        
        //5. 更新密码
        userMapper.updatePwd(user);
    }
} 