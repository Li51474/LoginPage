package com.hati.aiformat.mapper;

import com.hati.aiformat.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    //新增文章
    @Insert("insert into article(title, content, status, category_id, creator_id, create_time, update_time)" +
            " values(#{title}, #{content}, #{status}, #{categoryId}, #{creatorId}, #{createTime}, #{updateTime})")
    void add(Article article);

    //根据id查询文章
    @Select("select * from article where id = #{id}")
    @Results({
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "creator_id", property = "creatorId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    Article getById(Integer id);

    //根据id删除文章
    @Delete("delete from article where id = #{id}")
    void deleteById(Integer id);

    //更新文章
    @Update("update article set title = #{title}, content = #{content}, status = #{status}, " +
            "category_id = #{categoryId}, update_time = #{updateTime} where id = #{id}")
    void update(Article article);

    //查询文章列表
    @Select("<script>" +
            "select * from article " +
            "<where>" +
            "<if test='status != null'> status = #{status} </if>" +
            "</where>" +
            "</script>")
    @Results({
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "creator_id", property = "creatorId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<Article> list(Integer status);

    //根据分类查询文章
    @Select("select * from article where category_id = #{categoryId}")
    @Results({
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "creator_id", property = "creatorId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<Article> listByCategoryId(Integer categoryId);
}
