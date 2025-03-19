package com.hati.aiformat.mapper;

import com.hati.aiformat.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    
    //根据用户名查询用户（登录时使用）
    @Select("select * from user where username = #{username}")
    User findByUsername(String username);

    //根据ID查询用户（获取用户信息时使用）
    @Select("select * from user where id = #{id}")
    User findById(Integer id);

    //添加用户
    @Insert("insert into user(username, password, nickname, email, create_time, update_time)" +
            " values(#{username}, #{password}, #{nickname}, #{email}, now(), now())")
    void add(User user);

    //更新用户信息
    @Update("update user set " +
            "nickname=#{nickname}, " +
            "email=#{email}, " +
            "update_time=#{updateTime} " +
            "where id=#{id}")
    void update(User user);

    //更新密码
    @Update("update user set password=#{password}, update_time=#{updateTime} where id=#{id}")
    void updatePwd(User user);
} 
