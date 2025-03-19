package com.hati.aiformat.controller;

import com.hati.aiformat.pojo.Article;
import com.hati.aiformat.pojo.Result;
import com.hati.aiformat.service.ArticleService;
import com.hati.aiformat.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public Result add(@RequestBody @Validated Article article, HttpServletRequest request) {
        //获取登录用户的id
        Map<String, Object> claims = jwtUtil.getClaimsFromToken(request.getHeader("Authorization").substring(7));
        Integer userId = (Integer) claims.get("id");
        
        //设置参数
        article.setCreatorId(userId);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        
        //调用service
        articleService.add(article);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id, HttpServletRequest request) {
        //获取登录用户的id
        Map<String, Object> claims = jwtUtil.getClaimsFromToken(request.getHeader("Authorization").substring(7));
        Integer userId = (Integer) claims.get("id");
        
        //调用service
        articleService.delete(id, userId);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated Article article, HttpServletRequest request) {
        //获取登录用户的id
        Map<String, Object> claims = jwtUtil.getClaimsFromToken(request.getHeader("Authorization").substring(7));
        Integer userId = (Integer) claims.get("id");
        
        //设置更新时间
        article.setUpdateTime(LocalDateTime.now());
        
        //调用service
        articleService.update(article, userId);
        return Result.success();
    }

    @GetMapping
    public Result<List<Article>> list(@RequestParam(required = false) Integer categoryId, 
                                    @RequestParam(required = false) Integer status) {
        List<Article> articles;
        if (categoryId != null) {
            articles = articleService.listByCategoryId(categoryId);
        } else if (status != null) {
            articles = articleService.list(status);
        } else {
            articles = articleService.list(null); // 查询所有状态的文章
        }
        return Result.success(articles);
    }

    @GetMapping("/{id}")
    public Result<Article> getById(@PathVariable Integer id) {
        Article article = articleService.getById(id);
        return Result.success(article);
    }
}
