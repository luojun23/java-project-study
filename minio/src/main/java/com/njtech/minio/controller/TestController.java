package com.njtech.minio.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : TestController
 * @Description :
 * @Author : 罗君
 * @Date: 2026/4/9
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String test() {
        return "hello world";
    }

    public static void main(String[] args) {
        String filename = "test.txt";
        System.out.println(filename.substring(filename.lastIndexOf(".") + 1));
    }
}
