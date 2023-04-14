package com.admin.dao.mapper;


import com.framework.dao.pojo.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("select * from my_comment where article_id=#{id} and level=1")
    List<Comment> findCommentByArticleId(long id);

    @Select("select * from my_comment where parent_id=#{id} and article_id=#{article_id}")
    List<Comment> findCommentByparentId(@Param("id") long id, @Param("article_id") long article_id);

    @Insert("insert into my_comment(content,createDate,article_id,parent_id,to_uid,level) values(#{content},#{createDate},#{article_id},#{parent_id},#{to_uid},#{level})")
    @Options(useGeneratedKeys = true,keyProperty ="id" )
    Long insertComment(Comment comment);

    @Select("select * from my_comment order by del_fag DESC")
    List<Comment> findComment();

    @Select("select title from my_article where id=#{article_id}")
    String findArticleNamById(long article_id);

    @Select("select * from my_comment where id=#{id} and del_fag=1")
    Comment findCommentById(Integer id);

    @Update("update my_comment set del_fag=1 where id=#{id}")
    void deleteComment(Integer id);
}
