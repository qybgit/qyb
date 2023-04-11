package com.admin.service;


import com.framework.vo.Result;
import com.framework.vo.params.Account;

public interface UserService {
    Result login(Account account);

    Result logout();

}
