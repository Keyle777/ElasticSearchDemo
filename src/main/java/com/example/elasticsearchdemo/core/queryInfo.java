package com.example.elasticsearchdemo.core;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.*;
import com.example.elasticsearchdemo.model.ArchiveLibrary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class queryInfo {
    @Autowired
    ElasticsearchClient client;
    private MatchAllQuery.Builder t;

    public HashMap<String, Object> paginateTheEntireContent(String indexName, String field, String query, Integer currentPage, Integer pageSize){
        log.info("开始查询："+indexName+"，索引库中所有数据，执行方法：paginateTheEntireContent()");
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
        for (Hit<ArchiveLibrary> hit: hits) {
            ArchiveLibrary archiveLibrary = hit.source();
            archiveLibraries.add(archiveLibrary);
        }
        hashMap.put("archiveLibraries",archiveLibraries);
        hashMap.put("total",total.value());
        hashMap.put("currentPage",currentPage);
        hashMap.put("pageSize",pageSize);
        return hashMap;
    }

}
