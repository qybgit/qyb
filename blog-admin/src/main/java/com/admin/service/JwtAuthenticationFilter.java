package com.admin.service;

import com.admin.dao.pojo.LoginUser;
import com.admin.dao.pojo.SysUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    SysUserService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("authorization");
        if (StringUtils.isBlank(token)) {
//            Result result = new Result(400, "请登录", null);
//            response.setContentType("application/json;charset=utf-8");
//            response.getWriter().print(JSON.toJSON(result));

            filterChain.doFilter(request, response);
            return;
        }
//        token = token.substring(7);
        SysUser sysUser;
        sysUser = service.checkToken(token);
        if (sysUser == null) {
//            Result result = new Result(407, "token失效请重新登录", null);
//            response.setContentType("application/json;charset=utf-8");
//            response.getWriter().print(JSON.toJSON(result));
            filterChain.doFilter(request, response);
            return;
        }
        sysUser.setToken(token);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(new LoginUser(sysUser), null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }


}

