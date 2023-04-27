package com.example.elasticsearchdemo.core;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.ScoreSort;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.*;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.util.ObjectBuilder;
import com.example.elasticsearchdemo.model.ArchiveLibrary;
import com.example.elasticsearchdemo.tool.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class queryInfo {
    @Autowired
    ElasticsearchClient client;

    @Value("${elasticsearch.indices-name}")
    private String elasticsearchIndex;

    public HashMap<String, Object> paginateTheEntireContent(String indexName, String field, String query, Integer currentPage, Integer pageSize) {
        log.info("开始查询：" + indexName + "，索引库中所有数据，执行方法：paginateTheEntireContent()");
        SearchResponse<ArchiveLibrary> search = null;
        try {
            search = client.search(s -> s
                            .index(indexName)
                            .query(q -> q.match(
                                    t -> t.field(field)
                                            .query(query)
                            ))
                            .from((currentPage - 1) * pageSize)
                            .size(pageSize),
                    ArchiveLibrary.class
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        TotalHits total = search.hits().total();
        boolean isExactResult = false;
        if (total != null) {
            isExactResult = total.relation() == TotalHitsRelation.Eq;
        }

        if (isExactResult) {
            log.info("There are " + total.value() + " results");
        } else {
            log.info("There are more than " + total.value() + " results");
        }

        List<Hit<ArchiveLibrary>> hits = search.hits().hits();
        ArrayList<ArchiveLibrary> archiveLibraries = new ArrayList<>();
        for (Hit<ArchiveLibrary> hit : hits) {
            ArchiveLibrary archiveLibrary = hit.source();
            archiveLibraries.add(archiveLibrary);
        }
        hashMap.put("archiveLibraries", archiveLibraries);
        hashMap.put("total", total.value());
        hashMap.put("currentPage", currentPage);
        hashMap.put("pageSize", pageSize);
        return hashMap;
    }


    public ArrayList<Object> first2QueryInfo(String sort, Integer sortWay, String danWeiId, String param, Integer pageNumber, Integer pageSize, Integer start, Integer end, List<Integer> contentTagList, String zrz) {
        try {
            SearchResponse<ArchiveLibrary> search = client.search(
                    a -> a.index(elasticsearchIndex)
                            .from(pageNumber)
                            .size(pageSize)
                            .postFilter(
                                    builder -> builder.range(
                                            builder1 -> builder1.gte(JsonData.of(start)).field("year")
                                                    .lte(JsonData.of(end)).field("year")
                                    )
                            )
                            .query(
                                    builder -> builder.bool(
                                            BoolQuery.of(
                                                    builder1 -> builder1.must(
                                                            Query.of(builder2 -> {
                                                                        if (zrz != null) {
                                                                            return builder2.term(builder3 -> builder3.field("zrz.keyword").value(zrz));
                                                                        }
                                                                        return builder2.matchAll(
                                                                                builder3 -> builder3
                                                                        );
                                                                    }
                                                            )).should(
                                                            Query.of(
                                                                    builder3 -> {
                                                                        if (contentTagList != null && !contentTagList.isEmpty()) {
                                                                            builder1.should(Query.of(builder2 -> builder2.match(
                                                                                    MatchQuery.of(
                                                                                            builder4 -> builder4.field("contentTags").query(contentTagList.toString())
                                                                                    ))));
                                                                        }
                                                                        return builder3.match(
                                                                                MatchQuery.of(
                                                                                        builder4 -> builder4.field("contentTags").query("")
                                                                                )
                                                                        );
                                                                    }
                                                            )
                                                    )
                                            )
                                    )
                            )
                            .query(
                                    builder -> builder.multiMatch(
                                            builder1 -> builder1.fields("qzh","dh").query(param).boost(1.4f).operator(Operator.Or)
                                    )
                            )
                            .sort(
                                    builder -> {
                                        if (sortWay != 0 && sort != null) {
                                            return builder.field(
                                                    builder1 -> builder1.field(sort).order(SortOrder.Asc)
                                            );
                                        } else if (sortWay == 0 && sort != null) {
                                            return builder.field(
                                                    builder1 -> builder1.field(sort).order(SortOrder.Desc)
                                            );
                                        } else {
                                            return builder.score(builder1 -> builder1.order(SortOrder.Desc));
                                        }
                                    }
                            )
                            .highlight(highlightBuilder -> highlightBuilder
                                    .preTags("<font color='red'>")
                                    .postTags("</font>")
                                    .requireFieldMatch(false) //多字段时，需要设置为false
                                    .fields("qzh", highlightFieldBuilder -> highlightFieldBuilder)
                                    .fields("dh", highlightFieldBuilder -> highlightFieldBuilder)
                            )
                    , ArchiveLibrary.class);
            List<Hit<ArchiveLibrary>> hitList = search.hits().hits();
            ArrayList<Object> arrayList = new ArrayList<>();
            for (Hit<ArchiveLibrary> archiveLibraryHit : hitList) {
                ArchiveLibrary archiveLibrary = archiveLibraryHit.source();
                Map<String, List<String>> highlight = archiveLibraryHit.highlight();
                archiveLibrary.setHighLight(highlight);
                arrayList.add(archiveLibrary);
            }
            return arrayList;
        } catch (IOException e) {
            return null;
        }
    }

    public void test (){
            String tm = "王保国";
            Integer year = 2021;
        // Search by product name
        Query tmData = MatchQuery.of(m -> m
                .field("tm")
                .query(tm)
        )._toQuery();
        // Search by max price
        Query yearData = RangeQuery.of(r -> r
                .field("year")
                .gte(JsonData.of(year))
        )._toQuery();

        try {
            SearchResponse<?> response = client.search(s -> s
                            .index("archive_library")
                            .query(q -> q
                                    .bool(b -> b
                                            .must(tmData)
                                            .must(yearData)
                                    )
                            )
                            // 高亮查询
                            .highlight(highlightBuilder -> highlightBuilder
                                    .preTags("<font color='red'>")
                                    .postTags("</font>")
                                    .requireFieldMatch(false) //多字段时，需要设置为false
                                    .fields("tm", highlightFieldBuilder -> highlightFieldBuilder)
                            ),
                    ArchiveLibrary.class
            );
            List<? extends Hit<?>> hits = response.hits().hits();
            for (Hit<?> hit : hits) {
                System.out.println(hit);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
