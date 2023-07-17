package com.fw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fw.dto.UserInfoDto;
import com.fw.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-07-05 17:09:01
 */
public interface UserService extends IService<User> {

    UserInfoDto userInfo();

    boolean updateInfo(User user);

    boolean register(User user);

}
