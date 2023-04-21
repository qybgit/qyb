package com.admin.dao.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Role {
    private int id;
    private String name;
    private String role_key;
    private long create_time;
    private int del_flag;
    private List<Menu> menuList;
}
