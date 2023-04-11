package com.api.service;

import com.api.dao.pojo.Tag;
import com.api.vo.Result;

import java.util.List;

public interface TagService {
    Result selectAll();


    Result addTag(String nickname);

    List<Tag> selectByArticleId(Long id);

    Result selectCount();
}
