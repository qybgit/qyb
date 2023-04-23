package com.admin.service.impl;

import com.admin.dao.mapper.RoleMapper;
import com.admin.dao.mapper.SysUserMapper;
import com.admin.dao.pojo.Role;
import com.admin.dao.pojo.SysUser;
import com.admin.service.SysUserService;
import com.admin.util.JwtUtil;
import com.admin.vo.RoleVo;
import com.admin.vo.SysUserVoAdmin;
import com.admin.vo.params.SysDeleteParam;
import com.alibaba.fastjson2.JSON;

import com.framework.vo.Result;
import com.framework.vo.SysUserVo;
import com.admin.vo.params.Account;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SysUserServiceimpl implements SysUserService {
    @Resource
    SysUserMapper sysUserMapper;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Resource
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Resource
    RoleMapper roleMapper;

    @Override
    public SysUserVo selectUserById(long to_uid) {
        SysUser sysUser = sysUserMapper.selectUserById(to_uid);
        return copyUser(sysUser);
    }

    @Override
    public SysUser checkToken(String token) {

        Map<String, Object> map = JwtUtil.checkToken(token);
        if (map == null)
            return null;
        String o = redisTemplate.opsForValue().get(token);
        SysUser sysUser = JSON.parseObject(o, SysUser.class);


        return sysUser;
    }

    @Override
    public Result selectAllUser() {
        List<SysUser> sysUserList = sysUserMapper.selectAllUser();
        List<SysUserVoAdmin> sysUserVoList = new ArrayList<>();
        for (SysUser sysUser : sysUserList) {
            SysUserVoAdmin sysUserVo = copyUserAdmin(sysUser);
            sysUserVoList.add(sysUserVo);
        }
        return Result.success(sysUserVoList);
    }

    private SysUserVoAdmin copyUserAdmin(SysUser sysUser) {
        SysUserVoAdmin sysUserVo = new SysUserVoAdmin();
        BeanUtils.copyProperties(sysUser, sysUserVo);
        Role role=roleMapper.selectRoleByAuthor_id(sysUser.getId());
        if(role!=null){
            RoleVo roleVo=new RoleVo(role.getId(),role.getName(),role.getRole_key());
            sysUserVo.setRoleVo(roleVo);
        }else {
            sysUserVo.setRoleVo(null);
        }


        return sysUserVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result deleted(Long id) {
        SysUser sysUser = sysUserMapper.selectUserById(id);
        if (sysUser.getDeleted() == 1) {
            return Result.fail(400, "该用户已被删除", null);
        }
        if (!deletedUser(id)) {
            return Result.fail(400, "删除失败", null);
        }
        return Result.success("删除成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result editUser(SysUserVo sysUserVo) {

        if (StringUtils.isBlank(sysUserVo.getEmail())||StringUtils.isBlank(sysUserVo.getNickName())){
            return Result.fail(400,"请填写修改内容",null);
        }
        SysUser sysUser=sysUserMapper.selectUserById(sysUserVo.getId());
        SysUser sysUser1=sysUserMapper.selectUSerByName(sysUserVo.getNickName());//更改获取的用户名重复问题
        if (sysUser.getDeleted()==1){
            return Result.fail(400,"该用户已被停用",null);
        }
        if (sysUser1!=null&&sysUser1.getId()!=sysUserVo.getId()){
            return Result.fail(400,"该用户名重复",null);
        }
        if (!edit(sysUserVo)){
            return Result.fail(400,"修改失败",null);
        }
        return Result.success("修改成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addUser(Account account) {
        if (StringUtils.isBlank(account.getNickName()) && StringUtils.isBlank(account.getPassword())&&StringUtils.isBlank(account.getEmail())) {
            return Result.fail(400, "请填写信息 ", null);
        }
//        if (account.getRole()==null) {
//            return Result.fail(400, "请填写角色 ", null);
//        }
        SysUser sysUser1=sysUserMapper.selectUSerByName(account.getNickName());
        if(sysUser1!=null){
            return Result.fail(400,"账户名已存在",null);
        }
        SysUser sysUser = new SysUser();
        sysUser.setAccount( UUID.randomUUID().toString().replaceAll("-", ""));
        sysUser.setAdmin(1);
        sysUser.setType(1);
        sysUser.setNickName(account.getNickName());
        sysUser.setAvatar(null);
        sysUser.setCreate_date(System.currentTimeMillis());
        sysUser.setLast_login(System.currentTimeMillis());
        sysUser.setDeleted(0);
        sysUser.setEmail(account.getEmail());
        sysUser.setStatus(1);
        sysUser.setPasswordApi("ebedcb5c8f9264c285604fd87f99542a");
        sysUser.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        if(insertUser(sysUser, account.getRole())){
            return Result.success("添加成功");
        }
        return Result.fail(400,"添加失败",null);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result delete(SysDeleteParam sysDeleteParam) {
        sysUserMapper.updateUserState(sysDeleteParam.getDel_flag(),sysDeleteParam.getId());
        return Result.success("success");
    }

    private boolean insertUser(SysUser sysUser, Role role) {
        try {
            sysUserMapper.insertUser(sysUser);
            if (role.getId()!=null)
            {
                sysUserMapper.insertRoleWithUser(sysUser.getId(),role.getId());
            }

        }catch (Exception e){
            throw e;
        }
        return true;
    }

    private boolean edit(SysUserVo sysUserVo) {
        try {
            if(null != sysUserVo.getRoleId()){
                Role role=roleMapper.selectRoleByAuthor_id(sysUserVo.getId());
                if (role!=null){
                    roleMapper.updateRoleIdByAuthorId(sysUserVo.getRoleId(),sysUserVo.getId());
                }else {
                    roleMapper.insertRoleIdByAuthorId(sysUserVo.getRoleId(),sysUserVo.getId());
                }

            }
            sysUserMapper.updateUser(sysUserVo);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return  true;
    }

    private boolean deletedUser(Long id) {
        try {
            sysUserMapper.deleteUser(id);
            sysUserMapper.deleteUserWithRole(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return true;
    }

    private SysUserVo copyUser(SysUser sysUser) {
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);

        return sysUserVo;

    }
}
