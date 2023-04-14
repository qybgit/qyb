package com.api.service.impl;

import com.api.dao.mapper.SysUserMapper;
import com.api.dao.pojo.SysUser;
import com.api.service.LoginService;
import com.api.util.JwtUtil;
import com.api.vo.Result;
import com.api.vo.TokenVo;
import com.api.vo.params.Account;
import com.alibaba.fastjson2.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class LoginServiceimpl implements LoginService {
    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    RedisTemplate<String,String> redisTemplate;
    private String salt="qyb";
    @Override
    public Result register(Account account) {
        if (StringUtils.isBlank(account.getNickName()) && StringUtils.isBlank(account.getPassword())) {
            return Result.fail(400, "请填写登陆信息 ", null);
        }
        SysUser sysUser1=sysUserMapper.selectUSerByName(account.getNickName());
        if(sysUser1!=null){
            return Result.fail(400,"账户名已存在",null);
        }
        SysUser sysUser = new SysUser();
        sysUser.setAccount( UUID.randomUUID().toString().replaceAll("-", ""));
        sysUser.setAdmin(1);
        sysUser.setType(0);
        sysUser.setNickName(account.getNickName());
        sysUser.setAvatar(null);
        sysUser.setCreate_date(System.currentTimeMillis());
        sysUser.setLast_login(System.currentTimeMillis());
        sysUser.setDeleted(0);
        sysUser.setEmail(null);
        sysUser.setStatus(1);
        sysUser.setPasswordApi(DigestUtils.md2Hex(account.getPassword()+salt));
        sysUser.setPassword("$2a$10$MAksfrDau5B219ES3.dX0ebydYRF91g5bWTGMfBH2mHtIYXLJhLKS");
        if(addUser(sysUser)==true){
            return Result.success("注册成功");
        }
        return Result.fail(400,"注册失败",null);
    }

    @Override
    public Result login(Account account) {
        if (StringUtils.isBlank(account.getNickName())) {
            return Result.fail(400, "用户民错误", null);
        }
        if ( StringUtils.isBlank(account.getPassword())) {
            return Result.fail(400, " 请填写密码", null);
        }
        SysUser sysUser=sysUserMapper.selectUSerByName(account.getNickName());
        if (sysUser==null){
            return  Result.fail(400,"用户名不存在",null);
        }
        String pwd=DigestUtils.md2Hex(account.getPassword()+salt);
        if (!pwd.equals(sysUser.getPasswordApi())){
            return Result.fail(400,"密码错误",null);
        }

        String token= JwtUtil.createToken(sysUser.getId());
        TokenVo token1=new TokenVo(sysUser.getNickName(),token);
        redisTemplate.opsForValue().set(token, JSON.toJSONString(sysUser));
        redisTemplate.expire(token, 60*60*8, TimeUnit.SECONDS);

        return Result.success(token1);
    }

    @Override
    public Result logout() {
        return null;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(SysUser sysUser) {
        try{
            sysUserMapper.insertUser(sysUser);
        }catch (Exception e){
            throw e;
        }
        return true;
    }
}
