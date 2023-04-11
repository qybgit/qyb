package com.api.service;

import com.api.vo.CommentVo;
import com.api.vo.Result;
import com.api.vo.params.CommentParam;

import java.util.List;

public interface CommentService {
    List<CommentVo> findCommentByArticleId(long id);

    Result addComment(CommentParam commentParam);

}
