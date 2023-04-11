package com.api.dao.mapper;

import com.api.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchMapper {
    @Select("select * from my_article where title like concat('%',#{text},'%')")
    List<Article> search(String text);
}
