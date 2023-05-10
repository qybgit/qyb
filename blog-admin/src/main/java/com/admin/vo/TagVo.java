package com.admin.vo;

import com.framework.vo.SysUserVo;
import lombok.Data;

@Data
public class TagVo {
    private int id;
    private int del_flag;
    private String tag_Name;
    private int count;//评论数量
    private long create_date;
    private SysUserVo sysUserVo;
}
