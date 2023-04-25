package com.example.elasticsearchdemo.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("body1")
@ApiSupport(author = "xiaoymin@foxmail.com",order = 284)
@Tag(name = "测试控制器")
public class TestController {

    @Operation(summary = "所发即所得")
    @PostMapping("/mo")
    public String mo(String name){
        return name;
    }


}
