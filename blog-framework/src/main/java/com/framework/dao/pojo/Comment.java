package com.framework.dao.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Comment {
    private long id;
    private String content;
    private long createDate;
    private long article_id;
    private long parent_id;
    private long to_uid;
    private int level;
}
