package com.fw.Controller;

import com.fw.dto.BlogUserLoginDto;
import com.fw.entity.ResponseResult;
import com.fw.entity.User;
import com.fw.enums.AppHttpCodeEnum;
import com.fw.exception.SystemException;
import com.fw.service.BlogLoginService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BlogLoginController {

    @Resource
    BlogLoginService blogLoginService;

    @RequestMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        BlogUserLoginDto blogUserLoginDto=blogLoginService.login(user);
        if(blogUserLoginDto != null){
            return ResponseResult.okResult(blogUserLoginDto,"登录成功");
        }else {
            return ResponseResult.errorResult(201,"登录失败");
        }
    }

    @RequestMapping("/logout")
    public ResponseResult logout(){
        boolean flag=blogLoginService.logout();
        if(flag){
            return ResponseResult.okResult("注销成功");
        }else {
            return ResponseResult.errorResult(201,"注销失败");
        }
    }

}
