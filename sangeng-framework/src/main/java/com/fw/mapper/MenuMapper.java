package com.fw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fw.entity.Menu;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-12 14:39:12
 */
public interface MenuMapper extends BaseMapper<Menu> {


    @Select("SELECT perms FROM sys_menu WHERE id in (select menu_id from sys_role left join sys_role_menu on sys_role.id=sys_role_menu.role_id where sys_role.id=#{roleid}) and menu_type in ('C','F');")
    List<String> selectPermsByRole(Long roleid);

    @Select("SELECT * FROM sys_menu WHERE id in (select menu_id from sys_role left join sys_role_menu on sys_role.id=sys_role_menu.role_id where sys_role.id=#{roleid});")
    List<Menu> selectMenusByRole(Long roleid);

    List<Menu> selectMenuByUserid(Long userid);

    List<Menu> selectAllMenuByUserid(Long userid);

    @Update("UPDATE sys_menu SET del_flag=1 WHERE id=#{id} AND del_flag=0")
    int UpdateDel_flag(Long id);
}
