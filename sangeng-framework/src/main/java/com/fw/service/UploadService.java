package com.fw.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String uploadimg(MultipartFile multipartFile);
}
