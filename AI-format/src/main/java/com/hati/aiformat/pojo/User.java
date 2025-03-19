package com.hati.aiformat.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hati.aiformat.validation.ValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;
    
    @NotEmpty(message = "用户名不能为空", groups = {ValidationGroups.Add.class, ValidationGroups.Login.class})
    @Pattern(regexp = "^\\S{5,16}$", message = "用户名必须是5-16位非空字符", groups = {ValidationGroups.Add.class, ValidationGroups.Login.class})
    private String username;
    
    @NotEmpty(message = "密码不能为空", groups = {ValidationGroups.Add.class, ValidationGroups.Login.class})
    @Pattern(regexp = "^\\S{5,16}$", message = "密码必须是5-16位非空字符", groups = {ValidationGroups.Add.class, ValidationGroups.Login.class})
    @JsonIgnore //让springmvc把当前对象转换成json字符串的时候,忽略password
    private String password;
    
    @NotEmpty(message = "昵称不能为空", groups = {ValidationGroups.Update.class})
    @Pattern(regexp = "^\\S{1,10}$", message = "昵称必须是1-10位非空字符")
    private String nickname;
    
    @NotEmpty(message = "邮箱不能为空", groups = {ValidationGroups.Update.class})
    @Email(message = "邮箱格式不正确")
    private String email;
    
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 用于修改密码的字段
    @NotEmpty(message = "原密码不能为空", groups = {ValidationGroups.UpdatePwd.class})
    private String oldPwd;
    
    @NotEmpty(message = "新密码不能为空", groups = {ValidationGroups.UpdatePwd.class})
    @Pattern(regexp = "^\\S{5,16}$", message = "新密码必须是5-16位非空字符", groups = {ValidationGroups.UpdatePwd.class})
    private String newPwd;
    
    @NotEmpty(message = "确认密码不能为空", groups = {ValidationGroups.UpdatePwd.class})
    private String rePwd;
}

