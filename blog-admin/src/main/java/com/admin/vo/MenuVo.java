package com.admin.vo;

import com.admin.dao.pojo.Menu;
import lombok.Data;

import java.util.List;

@Data
public class MenuVo {
    private Long id;
    private String menu_name;
    private String icon;
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
    private List<MenuVo> children;
}
