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
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @GetMapping("first2QueryInfo")
    public RespBean first2QueryInfo(@RequestParam(name = "sort", required = false,value = "sort",defaultValue = "year") String sort,
                                  @RequestParam(name = "sortWay", required = false,value = "sortWay",defaultValue = "0") Integer sortWay,
                                  @RequestParam(name = "type", defaultValue = "关键字") String type,
                                  @RequestParam(name = "param", required = false) String param,
                                  @RequestParam(name = "start", required = false) Integer start,//开始时间
                                  @RequestParam(name = "end", required = false) Integer end,//结束时间
                                  @RequestParam(name = "zrz", required = false) String zrz,//责任者
                                  @RequestParam(name = "contentTagList", required = false) List<Integer> contentTagList,
                                  @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        ArrayList<Object> arrayList = queryInfo.first2QueryInfo(sort, sortWay, "52", param, pageNumber, pageSize, start, end, contentTagList, zrz);
        if(arrayList != null){
            return RespBean.success(arrayList);
        }
        return RespBean.error();
    }

    @GetMapping("/test")
    public void test(){
        queryInfo.test();
    }
}
