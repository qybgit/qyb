package com.framework.dao.mapper;



import com.framework.dao.pojo.Article;
import com.framework.dao.pojo.ArticleBody;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Select("select * from my_article")
    List<Article> findAllArticle();

    @Select("select * from my_article_body where id=#{body_id} ")
    ArticleBody findArticleBody(int body_id);

    @Select("select * from my_article where id=#{l}")
    Article selectById(Long l);

    @Select("select * from my_article where id=#{id}")
    Article selectArticleById(Long id);


    @Insert("insert into my_article(title,createDate,updateDate,summary,category_id,author_id) values(#{title},#{createDate},#{updateDate},#{summary},#{category_id},#{author_id})")
    @Options(useGeneratedKeys = true,keyProperty ="id" )
    Long insertArticle(Article article);


    @Insert("insert into my_article_body(article_id,content,content_html) values(#{article_id},#{content},#{content_html})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertBody(ArticleBody articleBody);

    @Update("update my_article set body_id=#{body_id} where id=#{id}")
    void update(Integer body_id,long id);

    @Select("SELECT COUNT(*) from my_article")
    int articleCount();

    @Select("select * from my_article")
    List<Article> alls();

    @Select("select my_article.id as id,title,createDate,updateDate,summary ,category_id from my_tag_article JOIN my_article ON my_article.id=my_tag_article.article_id where tag_id=#{id}")
    List<Article> selectArticleByTagId(Long id);
}
