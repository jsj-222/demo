package com.biocar.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author DeSen Xu
 * @date 2021-11-15 21:25
 */
@SpringBootTest
public class ArticleServiceTest {

    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Test
    public void searchTest() {
        // id: 226 207 39
        System.out.println(articleService.search("新冠", 1, 3));
    }
}
