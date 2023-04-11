package com.admin.service;

import com.alibaba.fastjson2.JSON;
import com.framework.vo.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Result result=new Result(400,"登陆成功",null);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSON.toJSON(result));
    }
}
