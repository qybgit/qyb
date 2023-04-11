package com.api.controller;

import com.api.service.LoginService;
import com.api.vo.Result;
import com.api.vo.params.Account;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RegisterController {

    @Resource
    LoginService loginService;

    /**
     * 注册
     * @param account
     * @return
     */
    @RequestMapping("register")
    public Result register(@RequestBody Account account){

        return loginService.register(account);
    }
}
