package com.hati.aiformat.service;

import com.hati.aiformat.pojo.Article;
import java.util.List;

public interface ArticleService {
    //新增文章
    void add(Article article);
    
    //删除文章（需要验证是否为文章作者）
    void delete(Integer id, Integer userId);
    
    //修改文章（需要验证是否为文章作者）
    void update(Article article, Integer userId);
    
    //查询文章列表（根据状态筛选）
    List<Article> list(Integer status);
    
    //根据id查询文章
    Article getById(Integer id);
    
    //根据分类查询已发布的文章
    List<Article> listByCategoryId(Integer categoryId);
}
