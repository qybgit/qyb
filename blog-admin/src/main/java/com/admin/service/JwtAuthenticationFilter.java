package com.admin.service;

import com.admin.dao.mapper.MenuMapper;
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
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    SysUserService service;
    @Resource
    MenuMapper menuMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("authorization");
        if (StringUtils.isBlank(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        token = token.substring(7);
        SysUser sysUser;
        sysUser = service.checkToken(token);
        if (sysUser == null) {
            filterChain.doFilter(request, response);
            return;
        }
        sysUser.setToken(token);
        List<String> permission;
        if (sysUser.getType()==1){
             permission=menuMapper.selectPermsByUserID(sysUser.getId());
        }else {
            permission=null;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(new LoginUser(sysUser,permission), null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }


}

