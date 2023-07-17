package com.fw.Controller;

import com.fw.annotation.SystemLog;
import com.fw.dto.LinkDto;
import com.fw.entity.ResponseResult;
import com.fw.service.LinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//和友链相关的接口实现
@RestController
@RequestMapping("/link")
public class LinkController {

    @Resource
    LinkService linkServices;

    @GetMapping("/getAllLink")
    @SystemLog(Remark = "获得所有友链")
    public ResponseResult getAllLink(){
        List<LinkDto> list=linkServices.getAllLink();
        return ResponseResult.okResult(list);
    }
}
