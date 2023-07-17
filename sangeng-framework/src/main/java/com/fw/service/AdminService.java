package com.fw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fw.dto.AdminDto;
import com.fw.entity.User;

//用于处理后台用户的服务
public interface AdminService extends IService<User> {
    AdminDto getInfo();
}
