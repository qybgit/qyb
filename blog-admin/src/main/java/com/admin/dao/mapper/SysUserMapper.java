package com.admin.dao.mapper;



import com.admin.dao.pojo.SysUser;
import com.framework.vo.SysUserVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysUserMapper {

    @Select("select * from  my_sys_user where id=#{to_uid}")
    SysUser selectUserById(long to_uid);

    @Insert("insert into my_sys_user(account,admin,avatar,create_date,deleted,email,nickname,last_login,password,status,passwordApi) values(#{account},#{admin},#{avatar},#{create_date},#{deleted},#{email},#{nickName},#{last_login},#{password},#{status},#{passwordApi})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insertUser(SysUser sysUser);

    @Select("select * from my_sys_user where nickname=#{nickName} and deleted=0")
    SysUser selectUSerByName(String nickName);

    @Select("select * from my_sys_user")
    List<SysUser> selectAllUser();
    @Select("select count(*) as count from my_sys_user where deleted=0")
    int selectAllUserDeleted();

    @Update("update my_sys_user set deleted=1 where id=#{id}")
    void deleteUser(Long id);

    @Update("update my_sys_user set email=#{email},nickName=#{nickName} where id=#{id}")
    void updateUser(SysUserVo sysUserVo);

    @Insert("insert into my_sys_role(author_id,role_id) values(#{author_id},#{role_id})")
    void insertRoleWithUser(@Param("author_id") long author_id, @Param("role_id") int role_id);

    @Update("update my_sys_role set del_flag=1 where author_id=#{id}")
    void deleteUserWithRole(Long id);

    @Update("update my_sys_user set deleted=#{del_flag} where id=#{id}")
    void updateUserState(@Param("del_flag") int del_flag, @Param("id") long id);
}
