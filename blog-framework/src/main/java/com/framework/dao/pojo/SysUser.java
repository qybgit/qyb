package com.framework.dao.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SysUser {
    private long id;
    private String account;
    private String nickName;
    private int admin;
    private String avatar;
    private long create_date;
    private long last_login;
    private int deleted;
    private String email;
    private String password;
    private String salt;
    private int status;
}
