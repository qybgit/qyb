package com.api.dao.pojo;

import lombok.Data;

@Data
public class ArticleBody {
    private int id;
    private long article_id;
    private String content;
    private String content_html;
}
