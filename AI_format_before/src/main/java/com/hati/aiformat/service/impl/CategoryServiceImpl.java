package com.hati.aiformat.service.impl;

import com.hati.aiformat.mapper.CategoryMapper;
import com.hati.aiformat.pojo.Category;
import com.hati.aiformat.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        //1. 检查分类名称是否已存在
        Category categoryByName = categoryMapper.findByName(category.getCategoryName(), category.getCreateUser());
        if (categoryByName != null) {
            throw new RuntimeException("分类名称已存在");
        }

        //2. 检查分类别名是否已存在
        Category categoryByAlias = categoryMapper.findByAlias(category.getCategoryAlias(), category.getCreateUser());
        if (categoryByAlias != null) {
            throw new RuntimeException("分类别名已存在");
        }

        //3. 新增分类
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list(Integer userId) {
        //直接调用mapper查询用户的分类列表
        return categoryMapper.list(userId);
    }

    @Override
    public List<Category> listAll() {
        //调用mapper查询所有分类列表
        return categoryMapper.listAll();
    }

    @Override
    public Category findById(Integer id) {
        //1. 查询分类是否存在
        Category category = categoryMapper.findById(id);
        if (category == null) {
            throw new RuntimeException("分类不存在");
        }
        return category;
    }

    @Override
    public void update(Category category) {
        //1. 查询分类是否存在
        Category dbCategory = categoryMapper.findById(category.getId());
        if (dbCategory == null) {
            throw new RuntimeException("分类不存在");
        }


        //3. 检查新的分类名称是否已存在
        Category categoryByName = categoryMapper.findByName(category.getCategoryName(), category.getCreateUser());
        if (categoryByName != null && !categoryByName.getId().equals(category.getId())) {
            throw new RuntimeException("分类名称已存在");
        }

        //4. 检查新的分类别名是否已存在
        Category categoryByAlias = categoryMapper.findByAlias(category.getCategoryAlias(), category.getCreateUser());
        if (categoryByAlias != null && !categoryByAlias.getId().equals(category.getId())) {
            throw new RuntimeException("分类别名已存在");
        }

        //5. 更新分类
        categoryMapper.update(category);
    }

    @Override
    public void delete(Integer id, Integer userId) {
        //1. 查询分类是否存在
        Category category = categoryMapper.findById(id);
        if (category == null) {
            throw new RuntimeException("分类不存在");
        }


        //3. 删除分类
        categoryMapper.delete(id);
        
        //4. 重排ID
        categoryMapper.reorderIds();
    }
}
