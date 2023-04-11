package com.admin.controller;
import com.admin.service.UserService;
import com.framework.vo.Result;
import com.framework.vo.params.Account;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin")
public class AdminLoginController {
    @Resource
    UserService userService;
    @GetMapping("login")
    public Result login(@RequestBody Account account){
        return userService.login(account);
    }
    @GetMapping("logout")
    public Result logout(){
        return userService.logout();
    }
}
