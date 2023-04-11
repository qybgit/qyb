package com.admin.dao.mapper;

import com.admin.dao.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper {
    @Select("select perms from my_sys_user su left join my_sys_role r on su.id=r.author_id LEFT JOIN my_role_menu sm on sm.role_id=r.role_id LEFT JOIN my_menu mm on sm.menu_id=mm.id where su.id=#{id} ")
    List<String> selectPermsByUserID(long id);
    @Select("select perms from my_menu where visible=1 and status=0")
    List<String> selectAll();

    @Select("select * from my_menu where parent_id=0")
    List<Menu> selectAllMenu();

    @Select("select * from my_menu where parent_id=#{id}")
    List<Menu> selectMenuByParentId(int id);
}
