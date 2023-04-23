package com.admin.controller;

import com.admin.service.SysUserService;
import com.admin.vo.params.SysDeleteParam;
import com.framework.vo.Result;
import com.framework.vo.SysUserVo;
import com.admin.vo.params.Account;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin/user")
public class AdminSysUserController {
    @Resource
    SysUserService sysUserService;

    /**
     * 获取所有用户
     * @return
     */

    @GetMapping("all")
    public Result allUser(){
        return sysUserService.selectAllUser();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @PostMapping("deleted/{id}")
    public Result deleted(@PathVariable("id") Long id){
        return sysUserService.deleted(id);
    }
    /**
     * 隐藏用户
     * @param
     * @return
     */
    @PostMapping("delete")
    public Result delete(@RequestBody SysDeleteParam sysDeleteParam){
        return sysUserService.delete(sysDeleteParam);
    }

    /**
     * 修改用户
     * @param sysUserVo
     * @return
     */
    @PostMapping("edit")
    public Result editUser(@RequestBody SysUserVo sysUserVo){
        return sysUserService.editUser(sysUserVo);
    }

    /**
     * 新增用户
     * @param account
     * @return
     */
    @PostMapping("addUser")
    public Result addUser(@RequestBody Account account){
        return sysUserService.addUser(account);
    }
}
