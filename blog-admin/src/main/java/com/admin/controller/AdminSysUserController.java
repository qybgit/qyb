package com.admin.controller;

import com.admin.service.SysUserService;
import com.framework.vo.Result;
import com.framework.vo.SysUserVo;
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
    @PostMapping("delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return sysUserService.delete(id);
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
}
