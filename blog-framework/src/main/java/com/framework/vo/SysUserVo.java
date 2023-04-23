package com.framework.vo;

import lombok.Data;
import lombok.ToString;

import javax.management.relation.Role;

@Data
@ToString
public class SysUserVo {
    private long id;
    private String account;
    private String email;
    private String avatar;
    private String nickName;
    private int deleted;
    private Integer roleId;

}
