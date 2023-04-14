package com.admin.service.impl;

import com.admin.dao.pojo.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("per")
public class PermissionService {
    public boolean hasPermission(String permission) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loginUser.getUser().getId() == 1404448588146192411l) {
            return true;
        }
        List<String> permissions=loginUser.getPermission();
        if (permissions==null||permissions.size()<1){
            return false;
        }
        return permissions.contains(permission);
    }
}
