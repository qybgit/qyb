package com.admin.service.impl;

import com.admin.dao.mapper.MenuMapper;
import com.admin.dao.pojo.LoginUser;
import com.admin.dao.pojo.Menu;
import com.admin.service.MenuService;
import com.admin.service.RolesService;
import com.admin.vo.AdminInfoVo;
import com.admin.vo.MenuVo;
import com.framework.vo.Result;
import com.framework.vo.SysUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceimpl implements MenuService {
    @Resource
    MenuMapper menuMapper;
    @Resource
    RolesService rolesService;
    @Override
    public Result getInfo() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> perms;
        if (loginUser.getUser().getId()==1404448588146192411l){
            perms=menuMapper.selectAll();
        }else {
            perms=menuMapper.selectPermsByUserID(loginUser.getUser().getId());
        }

        List<String> roleList=rolesService.selectRoleBYUserId(loginUser.getUser().getId());
        SysUserVo sysUserVo=new SysUserVo();
        BeanUtils.copyProperties(loginUser.getUser(),sysUserVo);
        AdminInfoVo adminInfoVo=new AdminInfoVo(perms,roleList,sysUserVo);
        return Result.success(adminInfoVo);
    }

    @Override
    public Result getRouters() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Menu> menuList=menuMapper.selectAllMenu();
        List<MenuVo> menuVos=copyList(menuList);
        return Result.success(menuVos);
    }

    private List<MenuVo> copyList(List<Menu> menuList) {
        List<MenuVo> menuVoList=new ArrayList<>();
        for (Menu menu:menuList){
            MenuVo menuVo=copy(menu);
            menuVoList.add(menuVo);
        }
        return menuVoList;
    }

    private MenuVo copy(Menu menu) {
        MenuVo menuVo=new MenuVo();
        BeanUtils.copyProperties(menu,menuVo);
        List<Menu> menuList=menuMapper.selectMenuByParentId(menu.getId());
        if (menuList!=null&&menuList.size()>0){
            menuVo.setChildren(menuList);
        }else {
            menuVo.setChildren(null);
        }
        return menuVo;
    }
}
