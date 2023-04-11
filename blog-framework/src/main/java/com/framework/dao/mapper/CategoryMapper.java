package com.framework.dao.mapper;


import com.framework.dao.pojo.Article;
import com.framework.dao.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("select * from my_category where id=#{category_id}")
    Category findById(int category_id);

    @Select("select * from my_article where category_id=#{id} ORDER BY createDate DESC")
    List<Article> selectArticle(int id);

    @Select("select category_name, category_id as id,count(*)as count from my_category JOIN my_article on my_category.id=my_article.category_id GROUP BY my_category.id")

    List<Category> selectAll();

}
