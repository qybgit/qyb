package com.admin.dao.mapper;


import com.framework.dao.pojo.Article;
import com.framework.dao.pojo.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("select * from my_category where id=#{category_id}")
    Category findById(int category_id);

    @Select("select * from my_article where category_id=#{id} and del_flag=0 ORDER BY createDate DESC")
    List<Article> selectArticle(int id);

    @Select("select id,create_date, category_name,createBy_id,del_flag from my_category  GROUP BY my_category.id order by del_flag ASC,create_date")

    List<Category> selectAll();

    @Update("update my_category set del_flag=1 where id=#{id}")
    int updateCategory(Integer id);
    @Insert("insert into my_category(category_name,createBy_id,del_flag,create_date) values(#{category_name},#{createBy_id},#{del_flag},#{create_date})")
    int insertCategory(Category category);

    @Select("select * from my_category where category_name=#{category_name} and del_flag=0")
    Category findByCategoryName(String category_name);


    @Select("select * from my_category where category_name=#{category_name} and del_flag=0")
    Category selectByCategoryName(String category_name);

    @Update("update my_category set category_name=#{category_name} where id=#{id} ")
    int updateTagByName(Category category);
}
