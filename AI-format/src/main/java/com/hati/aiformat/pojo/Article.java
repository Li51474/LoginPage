package com.hati.aiformat.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Article {
    private Integer id;//主键ID
    
    @NotEmpty(message = "文章标题不能为空")
    private String title;//文章标题
    
    @NotEmpty(message = "文章内容不能为空")
    private String content;//文章内容
    
    @NotNull(message = "文章分类不能为空")
    private Integer categoryId;//文章分类ID
    
    @NotNull(message = "发布状态不能为空")
    private Integer status;//发布状态 0-草稿 1-已发布
    
    private Integer creatorId;//创建人ID
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
} 