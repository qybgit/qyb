package com.admin.service.impl;


import com.admin.dao.pojo.LoginUser;
import com.admin.service.UserService;
import com.admin.util.JwtUtil;

import com.alibaba.fastjson2.JSON;
import com.framework.vo.Result;
import com.framework.vo.TokenVo;
import com.admin.vo.params.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceimpl implements UserService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public Result login(Account account) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account.getNickName(), account.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            if (authentication == null) {
                return Result.fail(500, "用户名或密码错误", null);
            }
            LoginUser user = (LoginUser) authentication.getPrincipal();
            String token = JwtUtil.createToken(user.getUser().getId());
            TokenVo token1 = new TokenVo(user.getUser().getNickName(), token);
            redisTemplate.opsForValue().set(token, JSON.toJSONString(user.getUser()));
            redisTemplate.expire(token, 60 * 60 * 24, TimeUnit.SECONDS);

            return Result.success(token1);
        } catch (Exception e) {
            return Result.fail(400, "redis error", null);
        }

    }

    @Override
    public Result logout() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        redisTemplate.delete(loginUser.getToken());
        return Result.success("退出成功");
    }
}
