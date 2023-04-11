package com.admin.service;

import java.util.List;

public interface RolesService {
    List<String> selectRoleBYUserId(long id);
}
