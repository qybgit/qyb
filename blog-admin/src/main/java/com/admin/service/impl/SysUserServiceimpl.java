package com.admin.service.impl;

import com.admin.dao.mapper.SysUserMapper;
import com.admin.dao.pojo.SysUser;
import com.admin.service.SysUserService;
import com.admin.util.JwtUtil;
import com.alibaba.fastjson2.JSON;
import com.framework.dao.pojo.Comment;

import com.framework.vo.Result;
import com.framework.vo.SysUserVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceimpl implements SysUserService {
    @Resource
    SysUserMapper sysUserMapper;
    @Autowired
    RedisTemplate<String, String> redisTemplate;

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
        List<SysUser> commentList = sysUserMapper.selectAllUser();
        List<SysUserVo> sysUserVoList = new ArrayList<>();
        for (SysUser sysUser : commentList) {
            SysUserVo sysUserVo = copyUser(sysUser);
            sysUserVoList.add(sysUserVo);
        }
        return Result.success(sysUserVoList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result delete(Long id) {
        SysUser sysUser = sysUserMapper.selectUserById(id);
        if (sysUser.getDeleted() == 1) {
            return Result.fail(400, "该用户已被删除", null);
        }
        if (!deleteUser(id)) {
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
        if (sysUser.getDeleted()==1){
            return Result.fail(400,"该用户已被删除",null);
        }
        if (!edit(sysUserVo)){
            return Result.fail(400,"修改失败",null);
        }
        return Result.success("修改成功");
    }

    private boolean edit(SysUserVo sysUserVo) {
        try {
            sysUserMapper.updateUser(sysUserVo);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return  true;
    }

    private boolean deleteUser(Long id) {
        try {
            sysUserMapper.deleteUser(id);
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
