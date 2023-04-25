package com.example.elasticsearchdemo.model.testEntry;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;


public class User {
    @Schema(description = "主键id",defaultValue = "1")
    private String id;

    @Schema(description = "名称",defaultValue = "张飞")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
