package com.biocar.controller;

import com.biocar.ExactlyResponseEntity;
import com.biocar.bean.Article;
import com.biocar.mapper.ArticleMapper;
import com.biocar.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.NoSuchElementException;

/**
 * @author DeSen Xu
 * @date 2021-11-07 11:41
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 查询文章
     * @param id 文章id
     * @return 文章信息
     */
    @GetMapping("/query")
    public ExactlyResponseEntity<Article> queryArticle(@RequestParam String id) {
        try {
            Article article = articleService.getArticle(id);
            return ExactlyResponseEntity.create(Article.class)
                    .data(article)
                    .build();
        } catch (NoSuchElementException e) {
            return ExactlyResponseEntity.create(Article.class)
                    .fail("该文章不存在")
                    .build();
        }
    }

    /**
     * 删除文章
     * @param id 文章id
     */
    @PostMapping("/delete")
    public ExactlyResponseEntity<Void> deleteArticle(@RequestParam("文章id") String id) {
        try {
            articleService.deleteArticle(id);
            return ExactlyResponseEntity.create(Void.class)
                    .build();
        } catch (NoSuchElementException e) {
            return ExactlyResponseEntity.create(Void.class)
                    .fail("该文章不存在")
                    .build();
        }
    }

    /**
     * 添加
     */
    @PostMapping("/insert")
    public ExactlyResponseEntity<Integer> addArticle(@RequestParam String title,
                                                     @RequestParam String body,
                                                     @RequestParam String source,
                                                     @RequestParam(required = false) String weight,
                                                     @RequestParam String keyword,
                                                     @RequestParam String imgUrl,
                                                     @RequestParam String sourceUrl,
                                                     @RequestParam(required = false) String articleType) {
        Article article = new Article();
        article.setTitle(title);
        article.setBody(body);
        article.setSource(source);
        article.setSourceUrl(sourceUrl);
        article.setKeyword(keyword);
        article.setImgUrl(imgUrl);
        if (articleType != null) {
            article.setArticleType(Integer.parseInt(articleType));
        }
        if (weight != null) {
            article.setWeight(new BigDecimal(weight));
        }
        Timestamp now = Timestamp.from(Instant.now());
        article.setStartedAt(now);
        article.setCreatedAt(now);
        article.setUpdatedAt(now);

        int id = articleService.addArticle(article);
        return ExactlyResponseEntity.create(Integer.class)
                .data(id)
                .build();

    }

    @PostMapping("/modify")
    public ExactlyResponseEntity<Void> updateArticle(@RequestParam String id, @RequestParam String key, @RequestParam String value) {
        Class<Article> articleClass = Article.class;
        Article article = new Article();

        if (ArticleMapper.COLUMN_ID.equals(key)) {
            return ExactlyResponseEntity.create(Void.class)
                    .fail("can not modify id!")
                    .build();
        }

        try {
            // 反射设置值
            Field declaredField = articleClass.getDeclaredField(key);
            // 获取类型
            Class<?> type = declaredField.getType();
            declaredField.setAccessible(true);

            Object articleValue;

            if (type.equals(Timestamp.class)) {
                // 日期判断
                articleValue = new Timestamp(Long.parseLong(value));
            } else if (type.equals(int.class)){
                // int
                articleValue = Integer.parseInt(value);
            } else if (type.equals(BigDecimal.class)){
                // BigDecimal
                articleValue = BigDecimal.valueOf(Double.parseDouble(value));
            } else {
                // String
                articleValue = type.cast(value);
            }
            declaredField.set(article, articleValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return ExactlyResponseEntity.create(Void.class)
                    .fail("key is invalid")
                    .build();
        } catch (ClassCastException e) {
            return ExactlyResponseEntity.create(Void.class)
                    .fail("value is invalid")
                    .build();
        } catch (NumberFormatException e) {
            return ExactlyResponseEntity.create(Void.class)
                    .fail("please give a valid timestamp")
                    .build();
        }

        try {
            // 设置id
            article.setId(Integer.parseInt(id));
            articleService.modifyArticle(article);
        } catch (NumberFormatException e) {
            return ExactlyResponseEntity.create(Void.class)
                    .fail("id must be a number")
                    .build();
        } catch (NoSuchElementException e) {
            return ExactlyResponseEntity.create(Void.class)
                    .fail("no such article can be found")
                    .build();
        }

        return ExactlyResponseEntity.create(Void.class)
                .success()
                .build();
    }

}
