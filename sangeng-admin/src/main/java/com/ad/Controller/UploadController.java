package com.ad.Controller;

import com.fw.entity.ResponseResult;
import com.fw.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
public class UploadController {

    @Resource
    UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadimg(MultipartFile img){
        return ResponseResult.okResult(uploadService.uploadimg(img));
    }

}
