package com.admin.service.impl;

import com.alibaba.fastjson2.JSON;
import com.framework.vo.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Service
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result result = new Result(400, "请登录", null);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSON(result));
    }
}
