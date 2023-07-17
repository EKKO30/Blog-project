package com.fw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fw.entity.RoleMenu;
import com.fw.mapper.RoleMenuMapper;
import com.fw.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-07-16 17:09:05
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}
