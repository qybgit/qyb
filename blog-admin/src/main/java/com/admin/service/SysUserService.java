package com.admin.service;



import com.admin.dao.pojo.SysUser;
import com.admin.vo.params.SysDeleteParam;
import com.framework.vo.Result;
import com.framework.vo.SysUserVo;
import com.admin.vo.params.Account;


public interface SysUserService {

    SysUserVo selectUserById(long to_uid);

    SysUser checkToken(String token);

    Result selectAllUser();

    Result deleted(Long id);

    Result editUser(SysUserVo sysUserVo);

    Result addUser(Account account);

    Result delete(SysDeleteParam sysDeleteParam);

    Result count();
}
