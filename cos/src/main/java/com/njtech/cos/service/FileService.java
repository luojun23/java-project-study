package com.njtech.cos.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
    //文件上传
    String upload(MultipartFile file);
}
