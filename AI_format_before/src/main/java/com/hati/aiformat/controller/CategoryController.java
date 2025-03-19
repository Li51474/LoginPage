package com.hati.aiformat.controller;

import com.hati.aiformat.pojo.Category;
import com.hati.aiformat.pojo.Result;
import com.hati.aiformat.service.CategoryService;
import com.hati.aiformat.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public Result add(@RequestBody Category category, HttpServletRequest request) {
        //1. 获取JWT中的用户ID
        String token = request.getHeader("Authorization");
        Map<String, Object> claims = jwtUtil.getClaimsFromToken(token.substring(7));
        Integer userId = (Integer) claims.get("id");
        
        //2. 设置分类的创建人和创建时间
        category.setCreateUser(userId);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        
        //3. 添加分类
        categoryService.add(category);
        return Result.success();
    }

    @GetMapping
    public Result<List<Category>> list() {
        //查询所有分类列表
        List<Category> categoryList = categoryService.listAll();
        return Result.success(categoryList);
    }

    @PutMapping
    public Result update(@RequestBody Category category, HttpServletRequest request) {
        //1. 设置更新时间
        category.setUpdateTime(LocalDateTime.now());
        
        //2. 设置创建人ID
        String token = request.getHeader("Authorization");
        Map<String, Object> claims = jwtUtil.getClaimsFromToken(token.substring(7));
        category.setCreateUser((Integer) claims.get("id"));
        
        //3. 更新分类
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam Integer id, HttpServletRequest request) {
        //1. 获取JWT中的用户ID
        String token = request.getHeader("Authorization");
        Map<String, Object> claims = jwtUtil.getClaimsFromToken(token.substring(7));
        Integer userId = (Integer) claims.get("id");
        //2. 删除分类
        categoryService.delete(id, userId);
        return Result.success();
    }
}
