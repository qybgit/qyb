package com.admin.vo;

import com.framework.vo.SysUserVo;
import lombok.Data;

@Data
public class CommentAdminVo {
    private long id;
    private String content;
    private String createDate;
    private int level;
    private SysUserVo toUser;
    private String articleName;
}
