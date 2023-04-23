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
import java.util.ArrayList;
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
        Role role=roleMapper.selectRoleById(id);
        List<Menu> menusId=menuMapper.selectMenuByRoleId(id);
        role.setMenuList(menusId);;
//        List<RoleVo> roleVoList;
//        if (id==1){
//             roleVoList=roleMapper.selectAllRole();
//        }else {
//            List<Integer> menusId=menuMapper.selectMenu_id(id);
//            if (menusId==null){
//                roleVoList=new ArrayList<>();
//                Role role=roleMapper.selectRoleById(id);
//                RoleVo roleVo=new RoleVo();
//                roleVo.setId(role.getId());
//                roleVo.setRole_key(role.getRole_key());
//                roleVo.setName(role.getName());
//                roleVo.setMenu_id(0);
//                roleVoList.add(roleVo);
//            }else {
//                roleVoList=roleMapper.selectRoleVoById(id);
//            }
//
//        }
        return Result.success(role);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result editRoles(RolesParam rolesParam) {
        if (rolesParam.getId()==1){

            return  Result.fail(400, "超级权限不可修改", null);
        }
        if (StringUtils.isBlank(rolesParam.getRoleName())||StringUtils.isBlank(rolesParam.getRole_key())){
            return  Result.fail(400, "请填写信息", null);
        }
//        Role role=roleMapper.selectRoleByName(rolesParam.getRoleName());
//        if (role!=null){
//            return  Result.fail(400, "角色名重复", null);
//        }
        if(!updateRole(rolesParam)){
            return  Result.fail(400, "修改失败", null);
        }
        return Result.success("修改成功");
    }

    private boolean updateRole(RolesParam rolesParam) {
        Role role=new Role();
        role.setId(rolesParam.getId());
        role.setRole_key(rolesParam.getRole_key());
        role.setName(rolesParam.getRoleName());
        try{
            roleMapper.updateRole(role);
            List<Integer> oldMenusList=menuMapper.selectMenu_id(role.getId());
            List<Integer> newMenusList=rolesParam.getMenusId();
            for (Integer id:oldMenusList){
                if (!newMenusList.contains(id)){
                    menuMapper.deleteMenuId(role.getId(),id);
                }
            }
            for (Integer id:newMenusList){
                if (!oldMenusList.contains(id)){
                    menuMapper.insertMenuId(role.getId(),id);
                }
            }
        }catch (Exception e){
            throw e;
        }

        return true;
    }

    private boolean insertRole(RolesParam rolesParam) {
        try {
            rolesParam.getRole().setCreate_time(System.currentTimeMillis());
            roleMapper.insertRole(rolesParam.getRole());
            if(rolesParam.getMenusId()!=null&&rolesParam.getMenusId().size()>0) {
                for (Integer id : rolesParam.getMenusId()) {
                    roleMapper.insertRoleWithMenu(rolesParam.getRole().getId(), id);
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return true;
    }
}
