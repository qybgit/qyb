package com.framework.dao.pojo;

import lombok.Data;

@Data
public class Category {
    private Integer id;
    private String category_name;
    private int del_flag;
    private long createBy_id;
    private int count;
    private long create_date;
}
