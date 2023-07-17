package com.fw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {

    UserInfoDto userInfoDto;

    List<String> permission;

    List<String> roles;
}
