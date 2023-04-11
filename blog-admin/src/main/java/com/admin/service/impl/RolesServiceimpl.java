package com.admin.service.impl;

import com.admin.dao.mapper.RoleMapper;
import com.admin.service.RolesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RolesServiceimpl implements RolesService {
    @Resource
    RoleMapper roleMapper;
    @Override
    public List<String> selectRoleBYUserId(long id) {
        List<String> roleList=roleMapper.selectRoleByUserId(id);
        return roleList;
    }
}
