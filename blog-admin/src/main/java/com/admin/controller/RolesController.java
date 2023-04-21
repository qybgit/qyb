package com.admin.controller;

import com.admin.service.RolesService;
import com.admin.vo.params.RolesParam;
import com.framework.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("system")
public class RolesController {
    @Resource
    RolesService rolesService;

    /**
     * 获取角色
     * @return
     */
    @GetMapping("allRole")
    public Result findRoles(){
        return rolesService.selectRolesList();
    }

    /**
     * 新增角色
     * @param rolesParam
     * @return
     */
    @PostMapping("addRole")
    public Result addRole(@RequestBody RolesParam rolesParam){
        return rolesService.addRole(rolesParam);
    }

    /**
     * 角色详情
     * @param id
     * @return
     */
    @PostMapping("role/{id}")
    public Result roleId(@PathVariable("id") Integer id){
        return rolesService.findRoleById(id);
    }

    /**
     * 修改角色信息
     * @param rolesParam
     * @return
     */
    @PostMapping("editRole")
    public Result editRole(@RequestBody RolesParam rolesParam ){
        return rolesService.editRoles(rolesParam);
    }

}
