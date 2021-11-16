package com.biocar.index;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biocar.bean.Article;
import com.biocar.mapper.ArticleMapper;
import com.biocar.service.EsArticleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author DeSen Xu
 * @date 2021-11-15 13:18
 */
@SpringBootTest
public class ArticleIndexTest {
    
    private RestHighLevelClient client;

    private ArticleMapper articleMapper;

    private EsArticleService esArticleService;

    @Autowired
    public void setEsArticleService(EsArticleService esArticleService) {
        this.esArticleService = esArticleService;
    }

    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Autowired
    public void setClient(RestHighLevelClient client) {
        this.client = client;
    }


    /**
     * 把mysql所有数据导入es中
     */
    @Test
    public void test() throws IOException {
        List<Map<String, Object>> articles = articleMapper.selectMaps(new QueryWrapper<Article>().select(ArticleMapper.COLUMN_ID, ArticleMapper.COLUMN_TITLE, ArticleMapper.COLUMN_BODY));
        System.out.println(articles.size());
        BulkRequest bulkRequest = new BulkRequest();
        for (Map<String, Object> article : articles) {
            Long id = (Long) article.remove(ArticleIndex.COLUMN_ID);
            System.out.println(id + "/" + articles.size());
            bulkRequest.add(new IndexRequest(ArticleIndex.INDEX_NAME)
                    .id(String.valueOf(id))
                    .source(new ObjectMapper().writeValueAsString(article), XContentType.JSON));
        }

        client.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    @Test
    public void search(){
        // ? ? ? 92 127 93
        System.out.println(esArticleService.search("新冠", 0, 6));
    }
    
}
