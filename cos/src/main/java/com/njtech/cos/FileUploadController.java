package com.njtech.cos;

import com.njtech.cos.common.Result;
import com.njtech.cos.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : FileUploadController
 * @Description :
 * @Author : 罗君
 * @Date: 2026/4/10
 */
@RestController
public class FileUploadController {
    @Resource
    private FileService fileService;
    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result upload(
            @RequestParam("file") MultipartFile file) {
        String uploadUrl = fileService.upload(file);
        Map<String,String> result = new HashMap<>();
        result.put("uploadUrl", uploadUrl);
        return Result.success("文件上传成功",result);
    }
}
