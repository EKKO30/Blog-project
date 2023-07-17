package com.ad.Controller;

import com.fw.entity.ResponseResult;
import com.fw.entity.Role;
import com.fw.enums.AppHttpCodeEnum;
import com.fw.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class RoleController {

    @Resource
    RoleService roleService;

    @GetMapping("/system/role/list")
    public ResponseResult getRoleList(int pageNum,int pageSize,String status,String name){
        return ResponseResult.okResult(roleService.getRoleList(pageNum,pageSize,status,name));
    }

    @PutMapping("/system/role/changeStatus")
    public ResponseResult changeStatus(@RequestBody Role role){
        return ResponseResult.okResult(roleService.changeStatus(role));
    }

    @PostMapping("/system/role")
    public ResponseResult addRole(@RequestBody Role role){
        return ResponseResult.okResult(roleService.addRole(role));
    }

    @GetMapping("/system/role/{id}")
    public ResponseResult getOne(@PathVariable Long id){
        Role t=roleService.getById(id);
        if(t != null ){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @GetMapping("/system/menu/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeselect(@PathVariable Long id){
        return ResponseResult.okResult(roleService.roleMenuTreeselect(id));
    }

    @PutMapping("/system/role")
    public ResponseResult update(@RequestBody Role role){
        boolean i=roleService.updateOne(role);
        if(i){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.errorResult(444,"更新失败");
        }
    }

    @DeleteMapping("/system/role/{id}")
    public ResponseResult articledel(@PathVariable Long id){
        if(roleService.delArticle(id)==1){
            return ResponseResult.okResult();
        }

        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }




}
