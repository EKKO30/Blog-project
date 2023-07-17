package com.fw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fw.dto.UserInfoDto;
import com.fw.entity.User;
import com.fw.enums.AppHttpCodeEnum;
import com.fw.exception.SystemException;
import com.fw.mapper.UserMapper;
import com.fw.service.UserService;
import com.fw.utils.BeanCopyUtils;
import com.fw.utils.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-07-05 17:09:02
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public boolean save(User entity) {
        return super.save(entity);
    }

    @Override
    public UserInfoDto userInfo() {
        Long userid= SecurityUtils.getUserId();
        User user = getById(userid);
        UserInfoDto userInfoDto = BeanCopyUtils.copyBean(user, UserInfoDto.class);
        return userInfoDto;
    }

    @Override
    public boolean updateInfo(User user) {
        boolean b = updateById(user);
        return b;
    }

    @Override
    public boolean register(User user) {
        //检查用户名是否存在
        if(existUserName(user)) {
            //说明数据库中已有此用户名
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }

        //检查邮箱是否存在
        if(existUserEmail(user)) {
            //说明数据库中已有此邮箱
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        //密码加密
        String EncodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(EncodePassword);

        //存入数据库
        save(user);

        return true;
    }


    public boolean existUserName(User user){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(User::getUserName,user.getUserName());
        int count = count(queryWrapper);
        return count>0;
    }

    public boolean existUserEmail(User user){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(User::getEmail,user.getEmail());
        int count = count(queryWrapper);
        return count>0;
    }
}
