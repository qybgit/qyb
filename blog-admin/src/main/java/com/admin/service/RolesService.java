package com.admin.service;

import com.admin.vo.params.RolesParam;
import com.framework.vo.Result;

import java.util.List;

public interface RolesService {
    List<String> selectRoleBYUserId(long id);

    Result selectRolesList();

    Result addRole(RolesParam rolesParam);

    Result findRoleById(Integer id);

    Result editRoles(RolesParam rolesParam);
}
