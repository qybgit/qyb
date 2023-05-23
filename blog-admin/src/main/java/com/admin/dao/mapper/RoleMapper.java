package com.admin.dao.mapper;

import com.admin.dao.pojo.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Select("select role_key from my_sys_user su left join my_sys_role sr on sr.author_id=su.id left join my_role r on sr.role_id=r.id where su.id=#{id} ")
    List<String> selectRoleByUserId(long id);

    @Select("select * from my_role order by id ASC ")
    List<Role> selectRolesList();

    @Select("select * from my_role where name=#{roleName} and del_flag=0")
    Role selectRoleByName(String roleName);

    @Insert("insert into my_role(name,role_key,create_time) values(#{name},#{role_key},#{create_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertRole(Role role);

    @Insert("insert into my_role_menu(role_id,menu_id) values(#{role_id},#{menu_id})")
    void insertRoleWithMenu(@Param("role_id") int role_id, @Param("menu_id") Integer menu_id);

//    @Select("SELECT mr.id,mr.name,menu_id,role_key,menu_name from my_role mr LEFT JOIN my_role_menu rm on mr.id=rm.role_id LEFT JOIN my_menu on rm.menu_id=my_menu.id where mr.id=#{id} and rm.del_flag=0  ")
//    List<RoleVo> selectRoleVoById(Integer id);

//    @Select("SELECT mr.id,mr.name,my_menu.id,role_key,menu_name from my_role mr LEFT JOIN my_menu on my_menu.visible=0 where mr.id=1 ")
//    List<RoleVo> selectAllRole();

    @Update("update my_role set name=#{name} ,role_key=#{role_key} where id=#{id}")
    void updateRole(Role role);

    @Select("select * from my_role where id=#{id} and del_flag=0")
    Role selectRoleById(Integer id);

    @Select("select role_id as id,name,role_key from my_sys_role LEFT JOIN my_role mr on my_sys_role.role_id=mr.id where author_id=#{id} and my_sys_role.del_flag=0")
    Role selectRoleByAuthor_id(long id);

    @Update("update my_sys_role set role_id=#{role_id} where author_id=#{author_id} and del_flag=0 ")
    void updateRoleIdByAuthorId(@Param("role_id") long role_id,@Param("author_id") long author_id);

    @Insert("insert into my_sys_role(role_id,author_id) values(#{role_id} ,#{author_id})")
    void insertRoleIdByAuthorId(@Param("role_id") long role_id,@Param("author_id") long author_id);

    @Select("select name,role_key from my_sys_role as sr left join my_role mr on sr.role_id=mr.id where author_id=#{id}")
    Role findRoleByUserId(Long id);
}
