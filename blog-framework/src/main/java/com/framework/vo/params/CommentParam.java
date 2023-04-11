package com.framework.vo.params;

import lombok.Data;

@Data
public class CommentParam {
    private String content;
    private int level;
    private long article_id;
    private long parent_id;
    private long to_uid;

}
