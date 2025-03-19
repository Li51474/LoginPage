package com.hati.aiformat.controller;

import com.hati.aiformat.pojo.Result;
import com.hati.aiformat.pojo.User;
import com.hati.aiformat.service.UserService;
import com.hati.aiformat.utils.JwtUtil;
import com.hati.aiformat.validation.ValidationGroups;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Validated(ValidationGroups.Add.class) User user){
        //调用service进行注册
        userService.register(user);
        return Result.success();
    }

    /**
     * 用户登录
     * @param user 登录用户信息（用户名，密码）
     * @return token令牌
     */
    @PostMapping("/login")
    public Result<String> login(@Validated(ValidationGroups.Login.class) User user) {
        // 调用service进行登录校验
        User loginUser = userService.login(user);
        // 登录成功，生成token
        String token = jwtUtil.generateToken(loginUser);
        // 把token存储到redis中
        stringRedisTemplate.opsForValue().set(token, token, 1, TimeUnit.HOURS);
        // 返回token
        return Result.success(token);
    }

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @GetMapping("/userInfo")
    public Result<User> userInfo(HttpServletRequest request) {
        // 从request中获取token
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            // 解析token
            Map<String, Object> claims = jwtUtil.getClaimsFromToken(token);
            // 获取用户ID
            Integer userId = (Integer) claims.get("id");
            // 调用service查询用户信息
            User user = userService.findById(userId);
            // 返回用户信息（密码不返回）
            user.setPassword(null);
            return Result.success(user);
        }
        return Result.error("未登录");
    }

    /**
     * 更新用户基本信息
     * @param user 用户信息（id, username, nickname, email）
     * @return 更新结果
     */
    @PutMapping("/update")
    public Result update(@Validated(ValidationGroups.Update.class) User user, HttpServletRequest request){
        // 获取当前登录用户ID
        String token = request.getHeader("Authorization");
        Map<String, Object> claims = jwtUtil.getClaimsFromToken(token.substring(7));
        Integer userId = (Integer) claims.get("id");
        // 设置用户ID
        user.setId(userId);
        // 调用service更新用户信息
        userService.update(user);
        return Result.success();
    }

    /**
     * 更新密码
     * @param old_pwd 原密码
     * @param new_pwd 新密码
     * @param re_pwd 确认密码
     * @return 更新结果
     */
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestParam("old_pwd") String oldPwd, 
                          @RequestParam("new_pwd") String newPwd, 
                          @RequestParam("re_pwd") String rePwd, 
                          HttpServletRequest request) {
        // 获取当前登录用户ID
        String token = request.getHeader("Authorization");
        Map<String, Object> claims = jwtUtil.getClaimsFromToken(token.substring(7));
        Integer userId = (Integer) claims.get("id");
        // 创建用户对象
        User user = new User();
        user.setId(userId);
        user.setOldPwd(oldPwd);
        user.setNewPwd(newPwd);
        user.setRePwd(rePwd);
        // 调用service更新密码
        userService.updatePwd(user);
        return Result.success();
    }
}

