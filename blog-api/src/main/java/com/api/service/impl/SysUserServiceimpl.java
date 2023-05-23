package com.api.service.impl;

import com.api.dao.mapper.SysUserMapper;
import com.api.dao.pojo.SysUser;
import com.api.service.SysUserService;
import com.api.util.JwtUtil;
import com.api.vo.SysUserVo;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class SysUserServiceimpl implements SysUserService {
    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    RedisTemplate<String,String> redisTemplate;
    @Override
    public SysUserVo selectUserById(long to_uid) {
        SysUser sysUser=sysUserMapper.selectUserById(to_uid);

        return copyUser(sysUser);
    }

    @Override
    public SysUser checkToken(String token) {

        Map<String,Object> map=JwtUtil.checkToken(token);
        if (map==null)
            return null;
        String o=  redisTemplate.opsForValue().get(token);
        SysUser sysUser=JSON.parseObject(o,SysUser.class);


        return sysUser;
    }

    private SysUserVo copyUser(SysUser sysUser) {
        SysUserVo sysUserVo=new SysUserVo();
        if (sysUser==null){
            sysUserVo.setNickName("此用户已被删除");
            return  sysUserVo;
        }
        BeanUtils.copyProperties(sysUser,sysUserVo);

        return sysUserVo;

    }
}
