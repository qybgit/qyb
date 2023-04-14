package com.admin.dao.mapper;


import com.framework.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchMapper {
    @Select("select * from my_article where title like concat('%',#{text},'%')")
    List<Article> search(String text);

    @Select("select my_article.id as id,title,createDate,updateDate,summary ,category_id from my_tag_article JOIN my_article ON my_article.id=my_tag_article.article_id where tag_id=#{tag_id} and category_id=#{category_id} and del_fag=0")
    List<Article> selectArticleByTagIdCategoryId(@Param("tag_id") Integer tag_id, @Param("category_id") Integer category_id);
}
