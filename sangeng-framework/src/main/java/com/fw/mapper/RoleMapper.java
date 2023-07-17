package com.fw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fw.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


/**
 * 角色信息表()表数据库访问层
 *
 * @author makejava
 * @since 2023-07-16 16:25:47
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Update("update sys_role set status=#{status} where id=#{id}")
    int updateStatusById(@Param("status") String status,@Param("id") Long id);

    @Update("UPDATE sys_role SET del_flag=1 WHERE id=#{id} AND del_flag=0")
    int UpdateDel_flag(Long id);
}
