package com.admin.vo;

import com.framework.vo.SysUserVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminInfoVo {
    private List<String> perms;
    private List<String> roles;
    private SysUserVo sysUserVo;
}
