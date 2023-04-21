package com.admin.dao.mapper;

import com.admin.dao.pojo.Role;
import com.admin.vo.RoleVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Select("select role_key from my_sys_user su left join my_sys_role sr on sr.author_id=su.id left join my_role r on sr.role_id=r.id where su.id=#{id} ")
    List<String> selectRoleByUserId(long id);

    @Select("select * from my_role order by id ASC ")
    List<Role> selectRolesList();

    @Select("select * from my_role where name=#{roleName}")
    Role selectRoleByName(String roleName);

    @Insert("insert into my_role(name,role_key) values(#{name},#{role_key})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertRole(Role role);

    @Insert("insert into my_role_menu(role_id,menu_id) values(#{role_id},#{menu_id})")
    void insertRoleWithMenu(@Param("role_id") int role_id, @Param("menu_id") Integer menu_id);

    @Select("SELECT mr.id,mr.name,menu_id,role_key,menu_name from my_role mr LEFT JOIN my_role_menu rm on mr.id=rm.role_id LEFT JOIN my_menu on rm.menu_id=my_menu.id where mr.id=#{id}  ")
    List<RoleVo> selectRoleById(Integer id);

    @Select("SELECT mr.id,mr.name,my_menu.id,role_key,menu_name from my_role mr LEFT JOIN my_menu on my_menu.visible=0 where mr.id=1 ")
    List<RoleVo> selectAllRole();
}
