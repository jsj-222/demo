package com.biocar.pojo;

import com.biocar.bean.Article;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

/**
 * @author DeSen Xu
 * @date 2021-11-15 13:15
 */
public class ArticleTest {

    @Test
    public void jsonTest() throws JsonProcessingException {
        String text = "{\"id\":1,\"title\":\"PD-L1/CTLA-4双抗KN046注册临床IND获批准，用于PD-(L)1治疗后进展的非小细胞肺癌\",\"articleType\":0,\"body\":\"hello world\",\"sourceUrl\":\"https://www.wuxuwang.com/zixun/78605309-1c13-11ec-b564-b8599fb66350\",\"source\":\"戊戌数据\",\"viewCount\":0,\"startedAt\":\"1636290385000\",\"weight\":5.55,\"keyword\":\"\",\"active\":0,\"isDelete\":0,\"createdAt\":\"1632844800000\",\"updatedAt\":\"1632844800000\",\"imgUrl\":\"https://file.wuxuwang.com/news/1632390728520-1629786207311-2021-08-24_142304.png\"}";
        Article article = new ObjectMapper().readValue(text, Article.class);
        System.out.println(article);
    }
}
