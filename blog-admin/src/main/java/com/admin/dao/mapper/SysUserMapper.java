package com.admin.dao.mapper;

import com.admin.dao.pojo.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysUserMapper {

    @Select("select * from  my_sys_user where id=#{to_uid}")
    SysUser selectUserById(long to_uid);

    @Insert("insert into my_sys_user(account,admin,avatar,create_date,deleted,email,nickname,last_login,password,status) values  (#{account},#{admin},#{avatar},#{create_date},#{deleted},#{email},#{nickName},#{last_login},#{password},#{status})")
    void insertUser(SysUser sysUser);

    @Select("select * from my_sys_user where nickname=#{nickName}")
    SysUser selectUSerByName(String nickName);
}
