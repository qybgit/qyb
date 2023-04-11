package com.api.handler;

import com.api.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
/**
 * 对加了Controller的注解进行拦截 Aop的实现
 */
public class AllExceptionHandler {
    /**
     * 处理Exception的异常进行处理
     * 返回json数据
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception e){
        e.printStackTrace();
        return Result.fail(-1,"系统异常",null);
    }
}
