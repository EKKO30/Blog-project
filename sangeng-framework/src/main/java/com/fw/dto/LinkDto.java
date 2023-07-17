package com.fw.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//展示到前端的友链
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_link")
public class LinkDto {
    @TableId
    private Long id;


    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;

}
