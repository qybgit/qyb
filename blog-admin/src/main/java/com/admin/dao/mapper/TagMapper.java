package com.admin.dao.mapper;


import com.framework.dao.pojo.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TagMapper {
    @Select("select * from my_tag")
    List<Tag> selectAll();

    @Select("SELECT my_tag.id,my_tag.tag_name from my_article JOIN my_tag_article ON my_article.id=my_tag_article.article_id JOIN my_tag ON my_tag_article.tag_id=my_tag.id where my_article.id=#{id}")
    List<Tag> selectArticleById(Long id);

    @Select("select article_id from my_tag_article where Tag_id=#{id}" )
    List<Long> selectAListId(Integer id);

    @Insert("insert into my_tag(tag_name,createBy_id,del_fag) values(#{tag_Name},#{createBy_id},#{del_fag})")
    void insertTag(Tag tag);

    @Select("select count(*) from my_tag_article where tag_id=#{id}")
    int findCount(int id);

    @Select("select count(*) from my_tag")
    int findTagCount();

    @Select("select * from my_tag where tag_name=#{nickName}")
    Tag selectByTagName(String nickname);

    @Update("update my_tag set del_fag=1 where id=#{id}")
    int updateTag(Integer id);

    @Select("select * from my_tag where id=#{id}")
    Tag selectById(Integer id);

    @Update("update my_tag set tag_name=#{tag_Name} where id=#{id}")
    int updateTagByName(Tag tag);
}
