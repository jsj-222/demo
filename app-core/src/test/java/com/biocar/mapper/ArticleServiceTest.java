package com.biocar.mapper;

import com.biocar.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author DeSen Xu
 * @date 2021-11-14 17:58
 */
@SpringBootTest
public class ArticleServiceTest {

    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Test
    public void test() {
        System.out.println(articleService.getArticles(2, 10));
    }
}
