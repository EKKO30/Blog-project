package com.fw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fw.dto.PageDto;
import com.fw.entity.ArticleTag;
import com.fw.entity.Menu;
import com.fw.entity.Role;
import com.fw.entity.RoleMenu;
import com.fw.mapper.MenuMapper;
import com.fw.mapper.RoleMapper;
import com.fw.mapper.RoleMenuMapper;
import com.fw.service.MenuService;
import com.fw.service.RoleMenuService;
import org.springframework.stereotype.Service;
import com.fw.service.RoleService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-07-16 16:27:21
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Resource
    RoleMapper roleMapper;

    @Resource
    RoleMenuService roleMenuService;

    @Resource
    RoleMenuMapper roleMenuMapper;

    @Resource
    MenuMapper menuMapper;

    @Resource
    MenuService menuService;

    @Override
    public PageDto getRoleList(int pageNum, int pageSize, String status, String name) {
        LambdaQueryWrapper<Role> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Role::getRoleName,name)
                .like(status!=null,Role::getStatus,status)
                .orderByAsc(Role::getRoleSort);


        Page<Role> Rolepage=new Page(pageNum,pageSize);
        page(Rolepage,queryWrapper);

        PageDto pageDto=new PageDto(Rolepage.getRecords(),Rolepage.getTotal());
        return pageDto;
    }


    @Override
    public Object changeStatus(Role role) {
        return roleMapper.updateStatusById(role.getStatus(),role.getId());

    }

    @Override
    public Object addRole(Role role) {
        save(role);

        List<RoleMenu> collect1 = role.getMenuIds()
                .stream()
                .map(id -> new RoleMenu(role.getId(), id))
                .collect(Collectors.toList());

        for (RoleMenu e:collect1
        ) {
            roleMenuService.save(e);
        }

        return true;
    }

    @Override
    public boolean updateOne(Role role) {
        Role role1 = getById(role.getId());
        if(role1.getMenuIds() == role1.getMenuIds()){
            return updateById(role);
        }

        LambdaQueryWrapper<RoleMenu> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,role.getId());

        roleMenuMapper.delete(queryWrapper);

        role.getMenuIds()
                .stream()
                .map(id ->roleMenuService.save(new RoleMenu(role.getId(),id)));

        return updateById(role);
    }

    //根据roleId查对应的权限树
    @Override
    public List<Menu> roleMenuTreeselect(Long id) {
        List<Menu> list = menuMapper.selectMenusByRole(id);

        List<Menu> list1 = builderMenuTree(list, 0L);
        return list1;
    }

    @Override
    public int delArticle(Long id) {
        return roleMapper.UpdateDel_flag(id);
    }

    //寻找根菜单，并保存子菜单
    private List<Menu> builderMenuTree(List<Menu> menus,Long id) {
        List<Menu> collect = menus.stream()
                .filter(menu -> menu.getParentId().equals(id))
                .map(menu -> menu.setChildren(getChildrens(menu,menus)))
                .collect(Collectors.toList());

        return collect;
    }

    //递归找到子菜单
    private List<Menu> getChildrens(Menu menu,List<Menu> menus) {
        List<Menu> collect = menus.stream()
                .filter(menu1 -> menu1.getParentId().equals(menu.getId()))
                .map(menu1 -> menu1.setChildren(getChildrens(menu1,menus)))
                .collect(Collectors.toList());

        return collect;
    }
}
























