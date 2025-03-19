package com.hati.aiformat.service.impl;

import com.hati.aiformat.mapper.ArticleMapper;
import com.hati.aiformat.pojo.Article;
import com.hati.aiformat.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        articleMapper.add(article);
    }

    @Override
    public void delete(Integer id, Integer userId) {
        //先查询文章是否存在
        Article article = articleMapper.getById(id);
        if(article == null) {
            throw new RuntimeException("文章不存在");
        }
        //删除文章
        articleMapper.deleteById(id);
    }

    @Override
    public void update(Article article, Integer userId) {
        //先查询文章是否存在
        Article dbArticle = articleMapper.getById(article.getId());
        if(dbArticle == null) {
            throw new RuntimeException("文章不存在");
        }
        //更新文章
        articleMapper.update(article);
    }

    @Override
    public List<Article> list(Integer status) {
        return articleMapper.list(status);
    }

    @Override
    public Article getById(Integer id) {
        return articleMapper.getById(id);
    }

    @Override
    public List<Article> listByCategoryId(Integer categoryId) {
        return articleMapper.listByCategoryId(categoryId);
    }
} 