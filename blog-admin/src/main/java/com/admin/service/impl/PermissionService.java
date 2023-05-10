package com.admin.service.impl;

import com.admin.dao.pojo.LoginUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("per")
public class PermissionService {
    public boolean hasPermission(String permission) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loginUser.getUser().getId() == 10000l) {
            return true;
        }
        List<String> permissions=loginUser.getPermission();
        if (permissions==null||permissions.size()<1){
            throw  new AccessDeniedException("权限不足");
        }
        if(!permissions.contains(permission)){
            throw  new AccessDeniedException("权限不足");
        }
        return true;
    }
}
