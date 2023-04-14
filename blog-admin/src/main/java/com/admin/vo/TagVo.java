package com.admin.vo;

import com.admin.dao.pojo.SysUser;
import com.framework.vo.SysUserVo;
import lombok.Data;

@Data
public class TagVo {
    private int id;
    private int del_fag;
    private String tag_Name;
    private int count;//评论数量
    private SysUserVo sysUserVo;
}
