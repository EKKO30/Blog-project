package com.fw.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * 用户表(User)表实体类
 *
 * @author makejava
 * @since 2023-06-08 17:42:00
 */
@SuppressWarnings("serial")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User implements Serializable{
    //主键@TableId
    @TableId
    private Long id;

    //用户名
    @NotEmpty(message ="用户名不能为空")
    @Length(max = 9,min = 2,message = "用户名长度在2~9之间")
    private String userName;
    //昵称
    @NotEmpty(message ="用户名不能为空")
    @Length(max = 9,min = 2,message = "用户名长度在2~9之间")
    private String nickName;
    //密码
    @NotBlank(message = "密码不能为空")
    @Length(min = 6,max = 20,message = "密码不能少于6位，也不能多于20位")
    private String password;
    //用户类型：0代表普通用户，1代表管理员
    private String type;
    //账号状态（0正常 1停用）
    private String status;
    //邮箱
    @Email(message = "邮箱不正确")
    private String email;
    //手机号
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;
    //创建人的用户id
    private Long createBy;
    //创建时间
    private Date createTime;
    //更新人
    private Long updateBy;
    //更新时间
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;



}
