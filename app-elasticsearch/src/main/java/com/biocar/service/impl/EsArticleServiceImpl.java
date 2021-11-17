package com.biocar.service.impl;

import com.biocar.index.ArticleIndex;
import com.biocar.service.EsArticleService;
import com.biocar.utils.EsUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

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
                .fetchSource(new FetchSourceContext(false, null, null))
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
    public void addArticle(String id, String title, String body) throws IOException, IllegalStateException {
        IndexRequest indexRequest = new IndexRequest(ArticleIndex.INDEX_NAME);
        Map<String, String> source = new HashMap<>(2);
        source.put(ArticleIndex.COLUMN_TITLE, title);
        source.put(ArticleIndex.COLUMN_BODY, body);
        indexRequest.source(source)
                .id(id);

        IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);
        if (EsUtils.checkFail(index)) {
            throw new IllegalStateException("添加文章失败");
        }
    }

    @Override
    public void modifyArticle(String id, String title, String body) throws IOException, NoSuchElementException {
        UpdateRequest updateRequest = new UpdateRequest(ArticleIndex.INDEX_NAME, id);
        Map<String, String> source = new HashMap<>(2);
        if (title != null) {
            source.put(ArticleIndex.COLUMN_TITLE, title);
        }
        if (body != null) {
            source.put(ArticleIndex.COLUMN_BODY, body);
        }
        updateRequest.doc(new ObjectMapper().writeValueAsString(source), XContentType.JSON);

        try {
            client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException e) {
            throw new NoSuchElementException("未找到该文章");
        }
    }

    @Override
    public void deleteArticle(String id) throws IOException, NoSuchElementException {
        DeleteRequest deleteRequest = new DeleteRequest(ArticleIndex.INDEX_NAME, id);

        DeleteResponse delete = client.delete(deleteRequest, RequestOptions.DEFAULT);

        if (EsUtils.checkFail(delete)) {
            throw new NoSuchElementException("未找到该文章");
        }

    }
}
