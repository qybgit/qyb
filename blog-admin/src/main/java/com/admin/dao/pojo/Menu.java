package com.admin.dao.pojo;

import lombok.Data;

@Data

public class Menu {
    private long id;
    private String menu_name;
    private String path;
    private String component;
    private int  vision;
    private int status;
    private String perms;
    private Long create_time;
    private Long update_time;
    private int del_flag;
    private int order_num;
    private long parent_id;
}
