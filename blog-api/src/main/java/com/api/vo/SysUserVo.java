package com.api.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SysUserVo {
    private long id;
    private String account;
    private String avatar;
    private String nickName;
    private int deleted;
}
