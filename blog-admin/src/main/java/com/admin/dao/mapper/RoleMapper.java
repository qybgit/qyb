package com.admin.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Select("select role_key from my_sys_user su left join my_sys_role sr on sr.author_id=su.id left join my_role r on sr.role_id=r.id where su.id=#{id} ")
    List<String> selectRoleByUserId(long id);
}
