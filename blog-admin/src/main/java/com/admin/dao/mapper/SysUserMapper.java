package com.admin.dao.mapper;



import com.admin.dao.pojo.SysUser;
import com.framework.vo.SysUserVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysUserMapper {

    @Select("select * from  my_sys_user where id=#{to_uid}")
    SysUser selectUserById(long to_uid);

    @Insert("insert into my_sys_user(account,admin,avatar,create_date,deleted,email,nickname,last_login,password,status) values  (#{account},#{admin},#{avatar},#{create_date},#{deleted},#{email},#{nickName},#{last_login},#{password},#{status})")
    void insertUser(SysUser sysUser);

    @Select("select * from my_sys_user where nickname=#{nickName}")
    SysUser selectUSerByName(String nickName);

    @Select("select * from my_sys_user")
    List<SysUser> selectAllUser();

    @Update("update my_sys_user set deleted=1 where id=#{id}")
    void deleteUser(Long id);

    @Update("update my_sys_user set email=#{email},nickName=#{nickName} where id=#{id}")
    void updateUser(SysUserVo sysUserVo);
}
