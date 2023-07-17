package com.fw.dto;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//分类选项
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    //分类名
    private String name;

    private String description;
}
