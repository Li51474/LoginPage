-- 创建数据库
CREATE DATABASE IF NOT EXISTS ai_format DEFAULT CHARSET utf8mb4;

-- 使用数据库
USE ai_format;

-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    username VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(32) NOT NULL COMMENT '密码',
    nickname VARCHAR(10) DEFAULT NULL COMMENT '昵称',
    email VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间'
) COMMENT '用户表';

-- 创建分类表
CREATE TABLE IF NOT EXISTS category (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    category_name VARCHAR(32) NOT NULL COMMENT '分类名称',
    category_alias VARCHAR(32) NOT NULL COMMENT '分类别名',
    create_user INT NOT NULL COMMENT '创建人ID',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    UNIQUE KEY unique_category (category_name, create_user),
    UNIQUE KEY unique_alias (category_alias, create_user)
) COMMENT '文章分类表'; 