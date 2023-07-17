package com.ad.Controller;

import com.fw.entity.Category;
import com.fw.entity.ResponseResult;
import com.fw.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class CategoryController {

    @Resource
    CategoryService categoryService;

    @GetMapping("/content/category/list")
    public ResponseResult CategoryList(int pageNum,int pageSize,String name,String status){
        return ResponseResult.okResult(categoryService.getAllList(pageNum,pageSize,name,status));
    }

    @PostMapping("/content/category")
    public ResponseResult addCategory(@RequestBody Category category){
        return ResponseResult.okResult(categoryService.addCategory(category));
    }

    @GetMapping("/content/category/{id}")
    public ResponseResult getcategory(@PathVariable Long id){
        return ResponseResult.okResult(categoryService.getById(id));
    }


    @PutMapping("/content/category")
    public ResponseResult updateOne(@RequestBody Category category){
        return ResponseResult.okResult(categoryService.updateOne(category));
    }

    @DeleteMapping("/content/category/{id}")
    public ResponseResult delCategory(@PathVariable Long id){
        return ResponseResult.okResult(categoryService.delCategory(id));
    }





}
