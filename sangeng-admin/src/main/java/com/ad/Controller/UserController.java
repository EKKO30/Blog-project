package com.ad.Controller;

import com.fw.dto.BlogUserLoginDto;
import com.fw.dto.LoginUserDto;
import com.fw.entity.ResponseResult;
import com.fw.entity.User;
import com.fw.enums.AppHttpCodeEnum;
import com.fw.exception.SystemException;
import com.fw.service.AdminLoginService;
import com.fw.service.AdminService;
import com.fw.service.UserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    AdminLoginService adminLoginService;

    @Resource
    AdminService adminService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            //必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        LoginUserDto loginUserDto = adminLoginService.login(user);
        if (loginUserDto != null) {
            return ResponseResult.okResult(loginUserDto, "登录成功");
        } else {
            return ResponseResult.errorResult(201, "登录失败");
        }
    }

    @GetMapping("/getInfo")
    public ResponseResult getInfo(){
        return ResponseResult.okResult(adminService.getInfo());
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        boolean flag=adminLoginService.logout();
        if(flag){
            return ResponseResult.okResult("注销成功");
        }else {
            return ResponseResult.errorResult(201,"注销失败");
        }
    }

}
