package com.admin.service;

import com.admin.vo.params.MenuParam;
import com.framework.vo.Result;

public interface MenuService {
    Result getInfo();

    Result getRouters();

    Result findMenus();

    Result findMenu(Long id);

    Result editMenu(MenuParam menuParam);

    Result getMenus();
}
