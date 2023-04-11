package com.framework.dao.pojo;

import lombok.Data;

@Data
public class Article {
    private  Long id;
    private String title;
    private long createDate;
    private long updateDate;
    private String summary;
    private int category_id;
    private long author_id;
    private int body_id;
}
