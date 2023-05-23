package com.api.dao.mapper;

import com.api.dao.pojo.Article;
import com.api.dao.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("select * from my_category where id=#{category_id} and del_flag=0")
    Category findById(int category_id);

    @Select("select * from my_article where category_id=#{id} ORDER BY createDate DESC")
    List<Article> selectArticle(int id);

    @Select("select category_name, category_id as id,count(*)as count from my_category JOIN my_article on my_category.id=my_article.category_id where my_category.del_flag=0 GROUP BY my_category.id")

    List<Category> selectAll();

}
