package com.fw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fw.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-08 17:41:59
 */
public interface UserMapper extends BaseMapper<User> {

    //通过userid查询对应的role关键字
    @Select("SELECT role_key FROM sys_role WHERE id = (SELECT role_id FROM sys_user LEFT JOIN sys_user_role ON sys_user.id = sys_user_role.user_id WHERE sys_user.id = #{id});")
    List<String> selectRoleByUser(Long id);

    //通过userid查询对应的roleid
    @Select("SELECT id FROM sys_role WHERE id = (SELECT role_id FROM sys_user LEFT JOIN sys_user_role ON sys_user.id = sys_user_role.user_id WHERE sys_user.id = #{id});")
    Long selectRoleidByUser(Long id);
}
