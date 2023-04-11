package com.api.util;

import com.api.dao.pojo.SysUser;
import com.api.service.SysUserService;
import com.api.vo.Result;
import com.alibaba.fastjson2.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class LoginInterceptor implements  HandlerInterceptor {
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    SysUserService service;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod))
            return true;//访问静态找资源
        String token=request.getHeader("authorization");

        if (StringUtils.isBlank(token)){
            Result result=new Result(400,"请登录",null);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSON(result));
            return false;

        }
        token=token.substring(7);
        SysUser sysUser=service.checkToken(token);
        if (sysUser==null){
            Result result=new Result(407,"token失效请重新登录",null);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSON(result));
            return false;
        }

        UserThreadLocal.put(sysUser);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("移除");
        UserThreadLocal.remove();

    }
}
