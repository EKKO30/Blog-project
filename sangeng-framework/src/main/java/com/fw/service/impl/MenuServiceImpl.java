package com.fw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fw.constant.SystemConstants;
import com.fw.entity.Menu;
import com.fw.enums.ServiceImplEnum;
import com.fw.mapper.MenuMapper;
import com.fw.service.MenuService;
import com.fw.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-07-12 14:39:14
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    MenuMapper menuMapper;

    @Override
    public List<Menu> getRouters(Long userid) {
        List<Menu> menus = null;

        //管理员有所有权限
        if(SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper
                    .in(Menu::getMenuType, Arrays.asList(ServiceImplEnum.MENU_MENU, ServiceImplEnum.MENU_contents))
                    .eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            menus = menuMapper.selectList(lambdaQueryWrapper);
        }else {
            menus= menuMapper.selectMenuByUserid(userid);
        }

        //构建tree
        List<Menu> list=builderMenuTree(menus,0L);

        return list;
    }

    //寻找根菜单，并保存子菜单
    private List<Menu> builderMenuTree(List<Menu> menus,Long id) {
        List<Menu> collect = menus.stream()
                .filter(menu -> menu.getParentId().equals(id))
                .map(menu -> menu.setChildren(getChildrens(menu,menus)))
                .collect(Collectors.toList());

        System.out.println(collect);
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

    @Override
    public List<Menu> getList(String name,String status) {
        LambdaQueryWrapper<Menu> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(name!=null,Menu::getMenuName,name)
                .like(status!=null,Menu::getStatus,status)
                .orderByAsc(Menu::getParentId,Menu::getOrderNum);

        List<Menu> list = list(lambdaQueryWrapper);
        return list;
    }

    @Override
    public boolean addMenu(Menu menu) {
        return save(menu);
    }

    @Override
    public boolean updateMenu(Menu menu) {
        return updateById(menu);
    }

    @Override
    public int delMenu(Long id) {
        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper=new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.eq(Menu::getParentId,id);
        List<Menu> list = list(menuLambdaQueryWrapper);

        //他是其他菜单的父id则不能删除
        if(list.size() > 0){
            return 0;
        }

        return menuMapper.UpdateDel_flag(id);
    }

    @Override
    public List<Menu> getTreeselect() {
        List<Menu> list = list();

        List<Menu> list1 = builderMenuTree(list, 0L);
        System.out.println("zzzz");
        return list1;
    }

}

















