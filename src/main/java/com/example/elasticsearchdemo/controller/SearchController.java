package com.example.elasticsearchdemo.controller;



import co.elastic.clients.elasticsearch.core.search.Hit;
import com.alibaba.fastjson2.JSON;
import com.example.elasticsearchdemo.core.queryInfo;
import com.example.elasticsearchdemo.model.ArchiveLibrary;
import com.example.elasticsearchdemo.tool.RespBean;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Tag(name = "搜索应用",description = "搜索")
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    queryInfo queryInfo;

    @GetMapping("/firstSearch")
    @Operation(summary = "根据输入的相关内容进行查询")
    @Parameters({
            @Parameter(name = "indexName",description = "索引库名称",required = true,example = "archive_library"),
            @Parameter(name = "field",description = "查询属性",required = true,example = "tm"),
            @Parameter(name = "query",description = "查询内容"),
            @Parameter(name = "currentPage",description = "当前页",example = "1"),
            @Parameter(name = "pageSize",description = "页面大小",example = "20")
    })
    public RespBean firstSearch(String indexName, String field, String query, Integer currentPage, Integer pageSize){
        HashMap<String,Object> result = queryInfo.paginateTheEntireContent(indexName, field, query, currentPage, pageSize);
        return RespBean.success("success", result);
    }

}
