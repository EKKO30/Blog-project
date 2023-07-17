package com.fw.service.impl;

import com.fw.dto.BlogUserLoginDto;
import com.fw.dto.UserInfoDto;
import com.fw.entity.LoginUser;
import com.fw.entity.User;
import com.fw.enums.AppHttpCodeEnum;
import com.fw.exception.SystemException;
import com.fw.service.BlogLoginService;
import com.fw.utils.BeanCopyUtils;
import com.fw.utils.JwtUtil;
import com.fw.utils.RedisCache1;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    RedisCache1 redisCache;

    @Override
    public BlogUserLoginDto login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken
                =new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());

        //代码会调用AuthenticationManager的authenticate方法进行认证。
        // 在认证过程中，会调用UserDetailsServiceImpl的loadUserByUsername方法来获取用户详细信息，
        // 使用该信息来创建一个已经认证的Authentication对象。
        Authentication authentication=authenticationManager.authenticate(authenticationToken);


        //获得用户信息
        LoginUser loginUser= (LoginUser) authentication.getPrincipal();
        String userid=loginUser.getUser().getId().toString();
        String jwt= JwtUtil.createJWT(userid);

        //将用户信息放入redis中
        redisCache.setCacheObject("bloglogin:"+userid,loginUser);

        //将token和userinfo封装返回
        UserInfoDto userInfoDto = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoDto.class);
        BlogUserLoginDto blogUserLoginDto=new BlogUserLoginDto(jwt,userInfoDto);

        return blogUserLoginDto;
    }

    @Override
    public boolean logout() {
        //获得当前的登录对象
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        //获得id
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userid=loginUser.getUser().getId().toString();
        //删除redis中的信息
        redisCache.deleteObject("bloglogin:"+userid);
        return true;
    }
}









