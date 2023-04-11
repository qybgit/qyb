package com.framework.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenVo {
    private String account;
    private String token;
}
