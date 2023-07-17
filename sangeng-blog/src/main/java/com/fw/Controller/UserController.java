package com.fw.Controller;

import com.fw.dto.UserInfoDto;
import com.fw.entity.ResponseResult;
import com.fw.entity.User;
import com.fw.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

//获得用户相关信息
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult getuserInfo(){
        UserInfoDto UserInfoDto=userService.userInfo();
        return ResponseResult.okResult(UserInfoDto);
    }

    @PutMapping("/userInfo")
    public ResponseResult updateInfo(@RequestBody User user){
        boolean f=userService.updateInfo(user);
        return ResponseResult.okResult();
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user){
        boolean f=userService.register(user);
        return ResponseResult.okResult();
    }

}
