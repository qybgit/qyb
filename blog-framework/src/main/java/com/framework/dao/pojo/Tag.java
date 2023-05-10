package com.framework.dao.pojo;

import lombok.Data;

@Data
public class Tag {
    private Integer id;
    private int del_flag;
    private String tag_Name;
    private int count;//评论数量
    private long createBy_id;
    private long create_date;
}
