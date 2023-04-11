package com.admin.service.impl;

import com.alibaba.fastjson2.JSON;
import com.framework.vo.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Service
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result result = new Result(400, "权限不足", null);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSON.toJSON(result));
    }
}
