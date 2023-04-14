package com.admin.dao.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class SysUser implements UserDetails {
    private long id;
    private String account;
    private String nickName;
    private int admin;
    private String avatar;
    private long create_date;
    private long last_login;
    private int deleted;
    private String email;
    private String password;
    private String salt;
    private int status;
    private String token;
    private String passwordApi;
    private int type;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
