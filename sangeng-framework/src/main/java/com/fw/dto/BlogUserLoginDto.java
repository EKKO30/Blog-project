package com.fw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogUserLoginDto {

    private String token;
    private UserInfoDto userInfo;
}