package com.admin.service.impl;

import com.admin.dao.mapper.MenuMapper;
import com.admin.dao.pojo.LoginUser;
import com.admin.dao.pojo.Menu;
import com.admin.service.MenuService;
import com.admin.service.RolesService;
import com.admin.vo.AdminInfoVo;
import com.admin.vo.MenuVo;
import com.admin.vo.params.MenuParam;
import com.framework.vo.Result;
import com.framework.vo.SysUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (loginUser.getUser().getId() == 10000l) {
            perms = menuMapper.selectAll();
        } else {
            perms = menuMapper.selectPermsByUserID(loginUser.getUser().getId());
        }

        List<String> roleList = rolesService.selectRoleBYUserId(loginUser.getUser().getId());
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(loginUser.getUser(), sysUserVo);
        AdminInfoVo adminInfoVo = new AdminInfoVo(perms, roleList, sysUserVo);
        return Result.success(adminInfoVo);
    }

    @Override
    public Result  getRouters() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Menu> menuList = menuMapper.selectAllMenu();
        List<MenuVo> menuVos = copyList1(menuList,true);
        return Result.success(menuVos);
    }

    @Override
    public Result findMenus() {
        List<Menu> menuList = menuMapper.selectMenuList();
        List<MenuVo> menuVoList=copyList(menuList,true);
        return Result.success(menuVoList);
    }

    @Override
    public Result findMenu(Long id) {
        Menu menu = menuMapper.selectMenuById(id);
        MenuVo menuVo = copy(menu,false);
        return Result.success(menuVo);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result editMenu(MenuParam menuParam) {
        menuMapper.editMenu(menuParam.getId(),menuParam.getMenuId());
        List<Menu> menuList = menuMapper.selectAllFirstMenu();
        List<MenuVo> menuVos = copyList(menuList,true);
        return Result.success(menuVos);
    }

    @Override
    public Result getMenus() {
        List<Menu> menuList = menuMapper.selectAllFirstMenu();
        List<MenuVo> menuVos = copyList(menuList,true);
        return Result.success(menuVos);
    }

    private List<MenuVo> copyList(List<Menu> menuList,boolean isChildren) {
        List<MenuVo> menuVoList = new ArrayList<>();
        for (Menu menu : menuList) {
            MenuVo menuVo = copy(menu,isChildren);
            menuVoList.add(menuVo);
        }
        return menuVoList;
    }

    private MenuVo copy(Menu menu,boolean isChildren) {
        MenuVo menuVo = new MenuVo();
        BeanUtils.copyProperties(menu, menuVo);
        if (isChildren){
            List<Menu> menuList = menuMapper.selectMenuByParentId(menu.getId());
            if (menuList != null && menuList.size() > 0) {
                List<MenuVo> menuVoList=copyList(menuList,true);
                menuVo.setChildren(menuVoList);
            } else {
                menuVo.setChildren(null);
            }
        }

        return menuVo;
    }
    private List<MenuVo> copyList1(List<Menu> menuList,boolean isChildren) {
        List<MenuVo> menuVoList = new ArrayList<>();
        for (Menu menu : menuList) {
            MenuVo menuVo = copy1(menu,isChildren);
            menuVoList.add(menuVo);
        }
        return menuVoList;
    }

    private MenuVo copy1(Menu menu,boolean isChildren) {
        MenuVo menuVo = new MenuVo();
        BeanUtils.copyProperties(menu, menuVo);
        if (isChildren){
            List<Menu> menuList = menuMapper.selectMenuByParentId1(menu.getId());
            if (menuList != null && menuList.size() > 0) {
                List<MenuVo> menuVoList=copyList(menuList,true);
                menuVo.setChildren(menuVoList);
            } else {
                menuVo.setChildren(null);
            }
        }

        return menuVo;
    }
}
