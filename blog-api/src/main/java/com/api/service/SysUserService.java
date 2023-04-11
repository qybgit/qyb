package com.api.service;

import com.api.dao.pojo.SysUser;
import com.api.vo.SysUserVo;

public interface SysUserService {

    SysUserVo selectUserById(long to_uid);

    SysUser checkToken(String token);
}
