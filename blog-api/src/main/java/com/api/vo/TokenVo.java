package com.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenVo {
    private String account;
    private String token;
    private String avatar;
}
