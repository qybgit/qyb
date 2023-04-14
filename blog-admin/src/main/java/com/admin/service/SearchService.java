package com.admin.service;


import com.admin.vo.params.SearchArticle;
import com.framework.vo.Result;

public interface SearchService {
    Result search(String text);

    Result searchArticle(SearchArticle searchArticle);
}
