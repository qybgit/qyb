package com.admin.vo.params;

import com.admin.dao.pojo.Role;
import lombok.Data;



@Data
public class Account {
    private String nickName;
//    private String avatar;
    private String password;
    private String email;
    private Role role;

}
