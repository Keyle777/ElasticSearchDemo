package com.example.elasticsearchdemo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "查询对象")
public class ArchiveLibrary {
    @Schema(description = "目录编号")
    private String catalogNumber;
    @Schema(description = "内容标签")
    private List<String> contentTags;
    @Schema(description = "单位ID")
    private Integer danwei;
    @Schema(description = "档号")
    private String dh;
    @Schema(description = "id")
    private Integer id;
    @Schema(description = "")
    private String idNumber;
    @Schema(description = "kfzt")
    private String kfzt;
    @Schema(description = "qzh")
    private String qzh;
    @Schema(description = "表ID")
    private Integer tablename;
    @Schema(description = "条目内容")
    private String tm;
    @Schema(description = "条目ID")
    private Integer tmid;
    @Schema(description = "条目名称")
    private String tmname;
    @Schema(description = "条目类型")
    private String tmtype;
    @Schema(description = "年限")
    private Integer year;
    @Schema(description = "责任人")
    private String zrz;
    @Schema(description = "高亮信息")
    private Map<String, List<String>> highLight;

}
