package com.ad.Controller;

import com.fw.dto.RouterDto;
import com.fw.entity.Menu;
import com.fw.entity.ResponseResult;
import com.fw.service.MenuService;
import com.fw.utils.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@RestController
public class MenuController {

    @Resource
    MenuService menuService;

    @GetMapping("/getRouters")
    public ResponseResult getRouters(){
        Long userid= SecurityUtils.getUserId();
        List<Menu> list=menuService.getRouters(userid);
        return ResponseResult.okResult(new RouterDto(list));
    }

    @GetMapping("/system/menu/list")
    public ResponseResult menuList(String name,String status){
        return ResponseResult.okResult(menuService.getList(name,status));
    }

    @GetMapping("/system/menu/{id}")
    public ResponseResult getid(@PathVariable Long id){
        return ResponseResult.okResult(menuService.getById(id));
    }

    @PutMapping("/system/menu")
    public ResponseResult updateMenu(@RequestBody Menu menu){
        if(menu.getId() == menu.getParentId()){
            return ResponseResult.errorResult(500,"不能选中自己为父id");
        }else {
            return ResponseResult.okResult(menuService.updateMenu(menu));
        }
    }

    @GetMapping("/system/menu/treeselect")
    public ResponseResult treeselect(){
        return ResponseResult.okResult(menuService.getTreeselect());
    }

}
