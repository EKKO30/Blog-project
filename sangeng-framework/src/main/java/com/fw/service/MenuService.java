package com.fw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fw.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-07-12 14:39:13
 */
public interface MenuService extends IService<Menu> {

    List<Menu> getRouters(Long userid);

    List<Menu> getList(String name,String status);

    boolean addMenu(Menu menu);

    boolean updateMenu(Menu menu);

    int delMenu(Long id);

    List<Menu> getTreeselect();
}
