package com.admin.controller;

import com.admin.service.MenuService;
import com.admin.vo.params.MenuParam;
import com.framework.vo.Result;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 获取所有menu包括删除的
     * @return
     */
    @GetMapping("getMenus")
    public Result getMenus(){

        return menuService.getMenus();
    }

    /**
     * 菜单列表
     * @return
     */
    @GetMapping("menus")
    public Result allMenus(){
        return menuService.findMenus();
    }

    /**
     * id菜单
     * @param id
     * @return
     */
    @GetMapping("menu/{id}")
    public Result menu(@PathVariable("id") Long id){
        return menuService.findMenu(id);
    }

    /**
     * 隐藏菜单
     * @param
     * @return
     */
    @PostMapping("menuEdit")
    public Result menuEdit(@RequestBody MenuParam menuParam){
        return menuService.editMenu(menuParam);
    }
}
