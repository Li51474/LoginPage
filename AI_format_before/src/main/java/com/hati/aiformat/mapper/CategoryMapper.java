package com.hati.aiformat.mapper;

import com.hati.aiformat.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    //新增分类
    @Insert("insert into category(name, description, creator_id)" +
            " values(#{categoryName}, #{categoryAlias}, #{createUser})")
    void add(Category category);

    //根据分类名称查询分类
    @Select("select id, name categoryName, description categoryAlias, creator_id createUser, create_time createTime, update_time updateTime from category where name = #{categoryName} and creator_id = #{createUser}")
    Category findByName(String categoryName, Integer createUser);

    //根据分类别名查询分类
    @Select("select id, name categoryName, description categoryAlias, creator_id createUser, create_time createTime, update_time updateTime from category where description = #{categoryAlias} and creator_id = #{createUser}")
    Category findByAlias(String categoryAlias, Integer createUser);

    //查询用户的分类列表
    @Select("select id, name categoryName, description categoryAlias, creator_id createUser, create_time createTime, update_time updateTime from category where creator_id = #{userId} order by id")
    List<Category> list(Integer userId);

    //查询所有分类列表
    @Select("select id, name categoryName, description categoryAlias, creator_id createUser, create_time createTime, update_time updateTime from category order by id")
    List<Category> listAll();

    //根据ID查询分类详情
    @Select("select id, name categoryName, description categoryAlias, creator_id createUser, create_time createTime, update_time updateTime from category where id = #{id}")
    Category findById(Integer id);

    //更新分类
    @Update("update category set name=#{categoryName}, description=#{categoryAlias}, update_time=#{updateTime} where id=#{id}")
    void update(Category category);

    @Delete("delete from category where id = #{id}")
    void delete(Integer id);

    //重排分类ID
    @Select("CALL reorder_category_ids()")
    void reorderIds();
}
