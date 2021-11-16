package com.biocar.service.impl;

import com.biocar.index.ArticleIndex;
import com.biocar.service.EsArticleService;
import lombok.SneakyThrows;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
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
import java.util.HashMap;
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

    @Override
    public List<Long> search(String keyword, int index, int max) throws IOException {
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

    @Override
    public void addArticle(String id, String title, String body) throws IOException {
        IndexRequest indexRequest = new IndexRequest(ArticleIndex.INDEX_NAME);
        HashMap<String, String> source = new HashMap<>(2);
        source.put(ArticleIndex.COLUMN_TITLE, title);
        source.put(ArticleIndex.COLUMN_BODY, body);
        source.put("k", body);
        indexRequest.source(source)
                .id(id);

        client.index(indexRequest, RequestOptions.DEFAULT);
    }
}
