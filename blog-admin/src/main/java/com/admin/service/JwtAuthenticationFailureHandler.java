package com.admin.service;

import com.alibaba.fastjson2.JSON;
import com.framework.vo.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtAuthenticationFailureHandler  extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Result result=new Result(500,"用户名或密码错误",null);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSON.toJSON(result));
    }
}
