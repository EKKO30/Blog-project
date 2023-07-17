package com.fw.service.impl;

import com.fw.dto.LoginUserDto;
import com.fw.dto.UserInfoDto;
import com.fw.entity.LoginUser;
import com.fw.entity.User;
import com.fw.enums.AppHttpCodeEnum;
import com.fw.exception.SystemException;
import com.fw.service.AdminLoginService;
import com.fw.utils.BeanCopyUtils;
import com.fw.utils.JwtUtil;
import com.fw.utils.RedisCache1;
import com.fw.utils.SecurityUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Resource
    RedisCache1 redisCache1;

    @Resource
    AuthenticationManager authenticationManager;

    @Override
    public LoginUserDto login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());

        Authentication authentication= authenticationManager.authenticate(authenticationToken);


        //代码会调用AuthenticationManager的authenticate方法进行认证。
        // 在认证过程中，会调用UserDetailsServiceImpl的loadUserByUsername方法来获取用户详细信息，
        // 使用该信息来创建一个已经认证的Authentication对象。
        LoginUser loginUser= (LoginUser) authentication.getPrincipal();
        String id=loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(id);

        redisCache1.setCacheObject("login:" + id,loginUser);

        UserInfoDto userInfoDto = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoDto.class);
        LoginUserDto loginUserDto=new LoginUserDto(jwt,userInfoDto);

        return loginUserDto;
    }

    @Override
    public boolean logout() {
        Long userid= SecurityUtils.getUserId();
        redisCache1.deleteObject("login"+userid);
        return true;
    }


}
