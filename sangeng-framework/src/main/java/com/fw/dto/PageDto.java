package com.fw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//用来表示封装dto
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {

    private List raw;
    private Long total;
}
