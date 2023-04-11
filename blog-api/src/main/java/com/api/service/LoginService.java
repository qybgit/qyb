package com.api.service;

import com.api.vo.Result;
import com.api.vo.params.Account;

public interface LoginService {
    Result register(Account account);

    Result login(Account account);

    Result logout();

}
