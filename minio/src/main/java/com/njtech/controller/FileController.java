package com.njtech.controller;

import com.njtech.common.Result;
import com.njtech.service.MinioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : FileController
 * @Description : 文件管理控制器
 * @Author : 罗君
 * @Date: 2026/4/9
 */
@Api(tags = "文件管理")
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private MinioService minioService;

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 上传结果
     */
    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public Result<Map<String, String>> uploadFile(
            @ApiParam("文件") @RequestParam("file") MultipartFile file) {
        String fileName = minioService.uploadFile(file);
        Map<String, String> result = new HashMap<>();
        result.put("fileName", fileName);
        return Result.success("文件上传成功", result);
    }

    /**
     * 获取文件URL
     *
     * @param fileName 文件名
     * @return 文件URL
     */
    @ApiOperation("获取文件URL")
    @GetMapping("/url")
    public Result<Map<String, String>> getFileUrl(
            @ApiParam("文件名") @RequestParam("fileName") String fileName) {
        String url = minioService.getFileUrl(fileName);
        Map<String, String> result = new HashMap<>();
        result.put("url", url);
        return Result.success("获取文件URL成功", result);
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名
     * @return 删除结果
     */
    @ApiOperation("删除文件")
    @DeleteMapping("/delete")
    public Result<Void> deleteFile(
            @ApiParam("文件名") @RequestParam("fileName") String fileName) {
        minioService.deleteFile(fileName);
        return Result.success("文件删除成功", null);
    }
}
