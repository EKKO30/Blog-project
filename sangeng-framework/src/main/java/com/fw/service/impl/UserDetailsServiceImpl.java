package com.fw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fw.entity.LoginUser;
import com.fw.entity.Menu;
import com.fw.entity.User;
import com.fw.enums.ServiceImplEnum;
import com.fw.mapper.MenuMapper;
import com.fw.mapper.UserMapper;
import com.fw.service.MenuService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    UserMapper userMapper;

    @Resource
    MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUserName,username);
        User user=userMapper.selectOne(queryWrapper);

        if(Objects.isNull(user)){
            throw new RuntimeException("用户名不存在");
        }

        List<Menu> menus = menuMapper.selectAllMenuByUserid(user.getId());
        List<String> Perms = menus.stream()
                .map(menu -> menu.getPerms())
                .collect(Collectors.toList());


        //查询权限信息封装
        //这只是对后台用户的权限管理，普通用户无法进入
        if(user.getPassword().equals(ServiceImplEnum.SUPER_USER)){
            return new LoginUser(user,Perms);
        }else {
            return new LoginUser(user,null);
        }
    }
}
