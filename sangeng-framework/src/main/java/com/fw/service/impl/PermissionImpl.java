package com.fw.service.impl;

import com.fw.entity.LoginUser;
import com.fw.enums.ServiceImplEnum;
import com.fw.utils.SecurityUtils;
import org.springframework.stereotype.Service;

@Service("ps")
public class PermissionImpl {
    public boolean haspermission(String permission){
        LoginUser loginUser = SecurityUtils.getLoginUser();

        //如果是超级管理员则直接返回true
        if(SecurityUtils.isAdmin()){
            return true;
        }else {
            return loginUser.getPermissions().contains(permission);
        }
    }

}
