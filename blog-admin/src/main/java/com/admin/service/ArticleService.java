package com.admin.service;


import com.framework.vo.ArticleVo;
import com.framework.vo.Result;
import com.framework.vo.params.ArticleParam;
import com.framework.vo.params.PageParams;
import org.springframework.stereotype.Service;

@Service
public interface ArticleService {

    Result findAllArticle(PageParams params);

    Result selectArticleByTagId(Integer id);

    Result selectArticle(int id);

    Result findArticle(Integer id);

    Result addArticle(ArticleParam articleParam);

    Result articleCount();

    Result alls();

    Result delete(Integer id);

    Result updateArticle(ArticleVo articleVo);

     Result articleByUid();

    Result allHome();
}
