package com.framework.service;




import com.framework.vo.CommentVo;
import com.framework.vo.Result;
import com.framework.vo.params.CommentParam;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CommentService {
    List<CommentVo> findCommentByArticleId(long id);

    Result addComment(CommentParam commentParam);

}
