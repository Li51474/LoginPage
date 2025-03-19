package com.hati.aiformat.service;

import com.hati.aiformat.pojo.Category;
import java.util.List;

public interface CategoryService {
    //新增分类
    void add(Category category);

    //查询分类列表
    List<Category> list(Integer userId);

    //查询所有分类列表
    List<Category> listAll();

    //根据ID查询分类详情
    Category findById(Integer id);

    //更新分类
    void update(Category category);

    void delete(Integer id, Integer userId);
}
