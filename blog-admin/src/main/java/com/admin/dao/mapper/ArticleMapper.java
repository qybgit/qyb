package com.admin.dao.mapper;



import com.framework.dao.pojo.Article;
import com.framework.dao.pojo.ArticleBody;
import com.framework.vo.ArticleBodyVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Select("select * from my_article where del_flag=0")
    List<Article> findAllArticle();

    @Select("select * from my_article_body where id=#{body_id} ")
    ArticleBody findArticleBody(int body_id);

    @Select("select * from my_article where id=#{l}")
    Article selectById(Long l);

    @Select("select * from my_article where id=#{id} and del_flag=0")
    Article selectArticleById(Integer id);


    @Insert("insert into my_article(title,createDate,updateDate,summary,category_id,author_id) values(#{title},#{createDate},#{updateDate},#{summary},#{category_id},#{author_id})")
    @Options(useGeneratedKeys = true,keyProperty ="id" )
    Long insertArticle(Article article);


    @Insert("insert into my_article_body(article_id,content,content_html) values(#{article_id},#{content},#{content_html})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertBody(ArticleBody articleBody);

    @Update("update my_article set body_id=#{body_id} where id=#{id} ")
    void update(@Param("body_id") Integer body_id,@Param("id") long id);

    @Select("SELECT COUNT(*) from my_article where del_flag=0")
    int articleCount();

    @Select("select * from my_article where del_flag=0")
    List<Article> alls();

    @Select("select my_article.id as id,title,createDate,updateDate,summary ,category_id from my_tag_article JOIN my_article ON my_article.id=my_tag_article.article_id where tag_id=#{id} and del_fag=0")
    List<Article> selectArticleByTagId(Integer id);

    @Update("update my_article set del_flag=1 where id=#{id}")
    int deleteArticle(Integer id);

    @Update("update my_comment set del_flag=1 where article_id=#{id}")
    int deleteComment(Integer id);

    @Insert("insert into my_tag_article(article_id,tag_id) values(#{article_id},#{tag_id}) ")
    int  insertArticleWithTag(@Param("tag_id") Integer tag_id, @Param("article_id") Long article_id);

    @Update("update my_tag_article set del_flag=1 where article_id=#{id}")
    int deleteTag(Integer id);

    @Update("update my_article set title=#{title},updateDate=#{updateDate},summary=#{summary},category_id=#{category_id} where id=#{id}")
    void updateArticle(Article article);

    @Update("update my_article_body set content=#{content} where id=#{id}")
    void updateArticleBody(ArticleBodyVo articleBodyVo);
    @Update("update my_tag_article set del_flag=1 where tag_id=#{id}")
    void deleteTagArticle(Integer id);

    @Insert("insert into my_tag_article(article_id,tag_id) values(#{article_id},#{id})")
    void insertTagArticle(@Param("article_id")Long article_id,@Param("id") Integer id);
}
