package com.admin.vo.params;

import com.admin.dao.pojo.Role;
import lombok.Data;

import java.util.List;

@Data
public class RolesParam {
    private Role role;
    private List<Integer> menusId;
    private String roleName;
    private String role_key;
    private int id;

}
