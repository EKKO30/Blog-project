package com.fw.service;

import com.fw.dto.LoginUserDto;
import com.fw.entity.User;

public interface AdminLoginService {

    LoginUserDto login(User user);

    boolean logout();
}
