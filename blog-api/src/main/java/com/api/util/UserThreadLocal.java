package com.api.util;


import com.api.dao.pojo.SysUser;

public class UserThreadLocal {
    private UserThreadLocal(){};//私有变量，不被实例化？
    private static final ThreadLocal<SysUser> LOCAL=new ThreadLocal<>();//线程隔离
    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }
    public static SysUser get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }
}
