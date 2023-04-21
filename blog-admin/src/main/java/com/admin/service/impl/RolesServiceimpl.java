package com.admin.service.impl;

import com.admin.dao.mapper.MenuMapper;
import com.admin.dao.mapper.RoleMapper;
import com.admin.dao.pojo.Menu;
import com.admin.dao.pojo.Role;
import com.admin.service.RolesService;
import com.admin.vo.RoleVo;
import com.admin.vo.params.RolesParam;
import com.framework.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RolesServiceimpl implements RolesService {
    @Resource
    RoleMapper roleMapper;
    @Resource
    MenuMapper menuMapper;

    @Override
    public List<String> selectRoleBYUserId(long id) {
        List<String> roleList = roleMapper.selectRoleByUserId(id);
        return roleList;
    }

    @Override
    public Result selectRolesList() {
        List<Role> roles = roleMapper.selectRolesList();
        for (Role role :roles){
            List<Menu> menuList;
            if (role.getId()==1){
                 menuList=menuMapper.selectMenuList();
            }else {
                menuList=menuMapper.selectMenuByRoleId(role.getId());
            }
            role.setMenuList(menuList);
        }
        return Result.success(roles);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addRole(RolesParam rolesParam) {
        if (StringUtils.isBlank(rolesParam.getRole().getName())){
            return Result.fail(400, "请填写", null);
        }
        Role role = roleMapper.selectRoleByName(rolesParam.getRole().getName());
        if (role != null) {
            return Result.fail(400, "此角色已存在", null);
        }
        if (!insertRole(rolesParam)){
            return Result.fail(400, "添加失败", null);
        }
        return Result.success("添加成功");
    }

    @Override
    public Result findRoleById(Integer id) {
        List<RoleVo> roleVoList;
        if (id==1){
             roleVoList=roleMapper.selectAllRole();
        }else {
            roleVoList=roleMapper.selectRoleById(id);
        }
        return Result.success(roleVoList);
    }

    @Override
    public Result editRoles(RolesParam rolesParam) {
        System.out.println(rolesParam);
        return null;
    }

    private boolean insertRole(RolesParam rolesParam) {
        try {
            roleMapper.insertRole(rolesParam.getRole());
            for (Integer id:rolesParam.getMenusId()){
                roleMapper.insertRoleWithMenu(rolesParam.getRole().getId(),id);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return true;
    }
}
