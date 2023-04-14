package com.admin.controller;

import com.admin.service.SysUserService;
import com.framework.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin/user")
public class SysUserController {
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
}
