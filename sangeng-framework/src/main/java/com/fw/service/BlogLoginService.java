package com.fw.service;

import com.fw.dto.BlogUserLoginDto;
import com.fw.entity.User;

public interface BlogLoginService {
    BlogUserLoginDto login(User user);

    boolean logout();
}
