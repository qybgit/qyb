package com.framework.dao.mapper;


import com.framework.dao.pojo.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper {
    @Select("select * from my_tag")
    List<Tag> selectAll();


    @Select("SELECT my_tag.id,my_tag.tag_name from my_article JOIN my_tag_article ON my_article.id=my_tag_article.article_id JOIN my_tag ON my_tag_article.tag_id=my_tag.id where my_article.id=#{id}")
    List<Tag> selectArticleById(Long id);

    @Select("select article_id from my_tag_article where Tag_id=#{id}" )
    List<Long> selectAListId(Long id);

    @Insert("insert into my_tag(tag_name) values(#{nickname})")
    void insertTag(String nickname);

    @Select("select count(*) from my_tag_article where tag_id=#{id}")
    int findCount(int id);

    @Select("select count(*) from my_tag")
    int findTagCount();
}
