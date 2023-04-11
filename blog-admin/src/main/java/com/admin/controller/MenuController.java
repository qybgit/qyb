package com.admin.controller;

import com.admin.service.MenuService;
import com.framework.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin")
public class MenuController {
    @Resource
    MenuService menuService;
    @GetMapping("getInfo")
    public Result getInfo(){

        return menuService.getInfo();
    }
    @GetMapping("getRouters")
    public Result getRouters(){

        return menuService.getRouters();
    }

}
