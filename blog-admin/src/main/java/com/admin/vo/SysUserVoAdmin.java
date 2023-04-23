package com.admin.vo;

import lombok.Data;

@Data
public class SysUserVoAdmin {
    private long id;
    private String account;
    private String email;
    private String avatar;
    private String nickName;
    private int deleted;
    private long create_date;
    private RoleVo roleVo;
}
