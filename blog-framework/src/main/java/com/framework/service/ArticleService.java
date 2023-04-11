package com.framework.service;


import com.framework.vo.Result;
import com.framework.vo.params.PageParams;
import org.springframework.stereotype.Service;

@Service
public interface ArticleService {

    Result findAllArticle(PageParams params);

    Result selectArticleByTagId(Long id);

    Result selectArticle(int id);

    Result findArticle(Long id);

//    Result addArticle(ArticleParam articleParam);

    Result articleCount();

    Result alls();
}
