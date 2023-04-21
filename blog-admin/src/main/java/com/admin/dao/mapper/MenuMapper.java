package com.admin.dao.mapper;

import com.admin.dao.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MenuMapper {
    @Select("select perms from my_sys_user su left join my_sys_role r on su.id=r.author_id LEFT JOIN my_role_menu sm on sm.role_id=r.role_id LEFT JOIN my_menu mm on sm.menu_id=mm.id where su.id=#{id} ")
    List<String> selectPermsByUserID(long id);
    @Select("select perms from my_menu where visible=1 and status=0")
    List<String> selectAll();

    @Select("select * from my_menu where parent_id=0  order by order_num DESC")
    List<Menu> selectAllFirstMenu();
    @Select("select * from my_menu where parent_id=0 and del_flag=0 order by order_num DESC")
    List<Menu> selectAllMenu();

    @Select("select * from my_menu where parent_id=#{id}")
    List<Menu> selectMenuByParentId(Long id);
    @Select("select * from my_menu where parent_id=#{id} and del_flag=0")
    List<Menu> selectMenuByParentId1(Long id);

    @Select("select * from my_menu where id=#{id}")
    Menu selectMenuById(Long id);

    @Select("select * from my_menu order by order_num DESC")
    List<Menu> selectMenuList();

    @Update("update my_menu set del_flag=#{id} where id=#{menuId}")
    void editMenu(@Param("id") Long id,@Param("menuId") Long menuId);

    //通过角色id查找其menu权限
    @Select("SELECT menu_name,my_menu.id from my_role mr LEFT JOIN my_role_menu rm on mr.id=rm.role_id LEFT JOIN my_menu on my_menu.id=rm.menu_id where mr.id=#{id}")
    List<Menu> selectMenuByRoleId(int id);
}
