package com.fw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fw.dto.PageDto;
import com.fw.entity.Menu;
import com.fw.entity.Role;
import com.fw.entity.Tag;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-07-16 16:27:20
 */
public interface RoleService extends IService<Role> {

    PageDto getRoleList(int pageNum, int pageSize, String status, String name);

    Object changeStatus(Role role);

    Object addRole(Role role);

    boolean updateOne(Role role);

    List<Menu> roleMenuTreeselect(Long id);

    int delArticle(Long id);
}
