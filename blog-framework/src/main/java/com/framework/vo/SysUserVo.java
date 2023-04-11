package com.framework.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SysUserVo {
    private long id;
    private String account;
    private int admin;
    private String avatar;
    private String nickName;
}
