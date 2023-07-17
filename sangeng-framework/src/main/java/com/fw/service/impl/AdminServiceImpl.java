package com.fw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fw.constant.SystemConstants;
import com.fw.dto.AdminDto;
import com.fw.dto.UserInfoDto;
import com.fw.entity.Menu;
import com.fw.entity.User;
import com.fw.enums.ServiceImplEnum;
import com.fw.mapper.MenuMapper;
import com.fw.mapper.UserMapper;
import com.fw.service.AdminService;
import com.fw.utils.BeanCopyUtils;
import com.fw.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//用于处理后台用户的服务
@Service
public class AdminServiceImpl extends ServiceImpl<UserMapper, User> implements AdminService {

    @Resource
    UserMapper userMapper;

    @Resource
    MenuMapper menuMapper;

    @Override
    public AdminDto getInfo() {
        //通过id获得user
        Long userid= SecurityUtils.getUserId();
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId,userid);
        User user = getOne(lambdaQueryWrapper);

        //获得用户信息和role
        UserInfoDto userInfoDto = BeanCopyUtils.copyBean(user, UserInfoDto.class);
        List<String> role=userMapper.selectRoleByUser(userid);

        List<Menu> list = null;

        //超级管理员有所有权限
        if(userid.toString().equals("1")){
            //获得相关权限列表
            LambdaQueryWrapper<Menu> lambdaQueryWrapper1=new LambdaQueryWrapper();

            lambdaQueryWrapper1.
                    in(Menu::getMenuType, Arrays.asList(ServiceImplEnum.MENU_MENU, ServiceImplEnum.MENU_BUTTON))
                    .eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);

            list= menuMapper.selectList(lambdaQueryWrapper1);

            List<String> perms = list.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());

            AdminDto adminDto=new AdminDto(userInfoDto,perms,role);
            return adminDto;
        }else {
            Long roleid=userMapper.selectRoleidByUser(userid);
            List<String> perms = menuMapper.selectPermsByRole(roleid);
            AdminDto adminDto=new AdminDto(userInfoDto,perms,role);
            return adminDto;
        }
    }
}

















