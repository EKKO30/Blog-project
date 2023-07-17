package com.ad.Controller;

import com.fw.entity.Category;
import com.fw.entity.Link;
import com.fw.entity.ResponseResult;
import com.fw.service.CategoryService;
import com.fw.service.LinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class LinkController {

    @Resource
    LinkService linkService;

    @GetMapping("/content/link/list")
    public ResponseResult LinkList(int pageNum, int pageSize, String name, String status){
        return ResponseResult.okResult(linkService.getAllList(pageNum,pageSize,name,status));
    }

    @PostMapping("/content/link")
    public ResponseResult addLink(@RequestBody Link link){
        return ResponseResult.okResult(linkService.addLink(link));
    }

    @GetMapping("/content/link/{id}")
    public ResponseResult getLink(@PathVariable Long id){
        return ResponseResult.okResult(linkService.getById(id));
    }


    @PutMapping("/content/link")
    public ResponseResult updateOne(@RequestBody Link link){
        return ResponseResult.okResult(linkService.updateOne(link));
    }

    @DeleteMapping("/content/link/{id}")
    public ResponseResult delLink(@PathVariable Long id){
        return ResponseResult.okResult(linkService.delLink(id));
    }


}
