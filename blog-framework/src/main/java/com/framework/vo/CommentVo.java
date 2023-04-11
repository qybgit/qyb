package com.framework.vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentVo {
    private long id;
    private String content;
    private String createDate;
    private int level;
    private SysUserVo toUser;
    private List<CommentVo> children;
}
