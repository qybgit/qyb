package com.admin.vo;

import com.framework.vo.SysUserVo;
import lombok.Data;

@Data
public class CategoryVo {
    private int id;
    private String category_name;
    private int del_flag;
    private SysUserVo sysUserVo;
    private int count;
    private long create_date;


}
