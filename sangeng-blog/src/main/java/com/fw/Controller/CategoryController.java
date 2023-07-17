package com.fw.Controller;

import com.fw.dto.CategoryDto;
import com.fw.entity.ResponseResult;
import com.fw.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    CategoryService categoryService;


    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        List<CategoryDto> list=categoryService.getCategoryList1();
        return ResponseResult.okResult(list);
    }
}
