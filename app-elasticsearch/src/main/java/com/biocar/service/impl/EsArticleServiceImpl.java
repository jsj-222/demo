package com.biocar.service.impl;

import com.biocar.index.ArticleIndex;
import com.biocar.service.EsArticleService;
import lombok.SneakyThrows;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author DeSen Xu
 * @date 2021-11-15 21:06
 */
@Service
public class EsArticleServiceImpl implements EsArticleService {

    private RestHighLevelClient client;

    public static final String ID_COLUMN = "_id";

    @Autowired
    public void setClient(RestHighLevelClient client) {
        this.client = client;
    }

    @SneakyThrows
    @Override
    public List<Long> search(String keyword, int index, int max) {
        SearchRequest article = new SearchRequest(ArticleIndex.INDEX_NAME);
        article.source(new SearchSourceBuilder()
                .fetchSource(new FetchSourceContext(false))
                .storedField(ID_COLUMN)
                .query(QueryBuilders
                        .boolQuery()
                        .must(QueryBuilders.matchQuery(ArticleIndex.COLUMN_TITLE, keyword))
                        .must(QueryBuilders.matchQuery(ArticleIndex.COLUMN_BODY, keyword)))
                .from(index * max)
                .size(max));

        SearchResponse search = client.search(article, RequestOptions.DEFAULT);

        // 填充id
        SearchHit[] hits = search.getHits().getHits();
        List<Long> ids = new ArrayList<>(hits.length);
        for (SearchHit hit : hits) {
            ids.add(Long.valueOf(hit.getId()));
        }
        return ids;
    }
}
