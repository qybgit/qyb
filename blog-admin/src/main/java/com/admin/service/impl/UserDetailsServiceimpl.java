package com.admin.service.impl;

import com.admin.dao.mapper.MenuMapper;
import com.admin.dao.mapper.SysUserMapper;
import com.admin.dao.pojo.LoginUser;
import com.admin.dao.pojo.SysUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDetailsServiceimpl implements UserDetailsService {
    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
        SysUser sysUser=sysUserMapper.selectUSerByName(nickName);
        if (sysUser==null){
                throw new UsernameNotFoundException("用户不存在");
            }
        if (sysUser.getType()==1){
            List<String> permission=menuMapper.selectPermsByUserID(sysUser.getId());
            return new LoginUser(sysUser,permission);
        }
        return new LoginUser(sysUser,null);
    }
}
