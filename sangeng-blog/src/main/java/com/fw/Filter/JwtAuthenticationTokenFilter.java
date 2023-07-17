package com.fw.Filter;


import com.alibaba.fastjson.JSON;
import com.fw.entity.LoginUser;
import com.fw.entity.ResponseResult;
import com.fw.enums.AppHttpCodeEnum;
import com.fw.utils.JwtUtil;
import com.fw.utils.RedisCache1;
import com.fw.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

//用于接受登录请求后解析token
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    RedisCache1 redisCache1;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //从请求头中获取token数据
        String token = httpServletRequest.getHeader("token");
        if(!StringUtils.hasText(token)){
            //说明该接口不需要登录直接放行
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        //解析token
        Claims claims=null;

        try {
            claims= JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token超时，token非法
            //响应前端重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }

        //获得userid
        String userid=claims.getSubject();

        //从redis中获得user
        LoginUser loginUser = redisCache1.getCacheObject("bloglogin:" + userid);
        if(loginUser == null){
            //说明登录过期  提示重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }

        //存入SecurityContextHolder,后面的过滤器从这里获得user
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                =new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

















