package com.admin.service.impl;

import com.admin.dao.mapper.SysUserMapper;
import com.admin.dao.pojo.LoginUser;
import com.admin.dao.pojo.SysUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDetailsServiceimpl implements UserDetailsService {
    @Resource
    SysUserMapper sysUserMapper;
    @Override
    public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
        System.out.println(nickName);

        SysUser sysUser=sysUserMapper.selectUSerByName(nickName);
        System.out.println(sysUser);
        if (sysUser==null){
                throw new UsernameNotFoundException("用户不存在");
            }
        return new LoginUser(sysUser);
    }
}
