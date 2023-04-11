package com.api.service;

import com.api.vo.Result;
import com.api.vo.params.ArticleParam;
import com.api.vo.params.PageParams;


public interface ArticleService {

    Result findAllArticle(PageParams params);

    Result selectArticleByTagId(Long id);

    Result selectArticle(int id);

    Result findArticle(Long id);

    Result addArticle(ArticleParam articleParam);

    Result articleCount();

    Result alls();
}
