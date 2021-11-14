package com.biocar.controller;

import com.biocar.ExactlyResponseEntity;
import com.biocar.bean.Article;
import com.biocar.mapper.ArticleMapper;
import com.biocar.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author DeSen Xu
 * @date 2021-11-07 11:41
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/article", produces = MediaType.APPLICATION_JSON_VALUE)
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

//    @PostMapping("/modify")
//    public ExactlyResponseEntity<Void> updateArticle(@RequestParam String id, @RequestParam String key, @RequestParam String value) {
//        Class<Article> articleClass = Article.class;
//        Article article = new Article();
//
//        if (ArticleMapper.COLUMN_ID.equals(key)) {
//            return ExactlyResponseEntity.create(Void.class)
//                    .fail("can not modify id!")
//                    .build();
//        }
//
//        try {
//            // 反射设置值
//            Field declaredField = articleClass.getDeclaredField(key);
//            // 获取类型
//            Class<?> type = declaredField.getType();
//            declaredField.setAccessible(true);
//
//            Object articleValue;
//
//            if (type.equals(Timestamp.class)) {
//                // 日期判断
//                articleValue = new Timestamp(Long.parseLong(value));
//            } else if (type.equals(int.class)){
//                // int
//                articleValue = Integer.parseInt(value);
//            } else if (type.equals(BigDecimal.class)){
//                // BigDecimal
//                articleValue = BigDecimal.valueOf(Double.parseDouble(value));
//            } else {
//                // String
//                articleValue = type.cast(value);
//            }
//            declaredField.set(article, articleValue);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            return ExactlyResponseEntity.create(Void.class)
//                    .fail("key is invalid")
//                    .build();
//        } catch (ClassCastException e) {
//            return ExactlyResponseEntity.create(Void.class)
//                    .fail("value is invalid")
//                    .build();
//        } catch (NumberFormatException e) {
//            return ExactlyResponseEntity.create(Void.class)
//                    .fail("please give a valid timestamp")
//                    .build();
//        }
//
//        try {
//            // 设置id
//            article.setId(Integer.parseInt(id));
//            articleService.modifyArticle(article);
//        } catch (NumberFormatException e) {
//            return ExactlyResponseEntity.create(Void.class)
//                    .fail("id must be a number")
//                    .build();
//        } catch (NoSuchElementException e) {
//            return ExactlyResponseEntity.create(Void.class)
//                    .fail("no such article can be found")
//                    .build();
//        }
//
//        return ExactlyResponseEntity.create(Void.class)
//                .success()
//                .build();
//    }
//
    @PostMapping("/modify")
    public ExactlyResponseEntity<Void> updateArticle(@RequestParam Integer id,
                                                     @RequestParam(required = false) String title,
                                                     @RequestParam(required = false) Integer articleType,
                                                     @RequestParam(required = false) String body,
                                                     @RequestParam(required = false) String sourceUrl,
                                                     @RequestParam(required = false) String source,
                                                     @RequestParam(required = false) Double weight,
                                                     @RequestParam(required = false) String keyword,
                                                     @RequestParam(required = false) Integer isDelete,
                                                     @RequestParam(required = false) String imgUrl) {
        Article article = new Article();
        article.setId(id);
        article.setTitle(title);
        article.setArticleType(articleType);
        article.setBody(body);
        article.setSourceUrl(sourceUrl);
        article.setSource(source);
        if (weight != null) {
            article.setWeight(BigDecimal.valueOf(weight));
        }
        article.setKeyword(keyword);
        if (isDelete != null) {
            article.setIsDelete(isDelete > 1 ? 1 : 0);
        }
        article.setImgUrl(imgUrl);
        try {
            articleService.modifyArticle(article);
        } catch (NoSuchElementException e) {
            return ExactlyResponseEntity.emptyBodyFail("未找到目标文章");
        }
        return ExactlyResponseEntity.emptyBodySuccess();
    }

    /**
     * 分页获取文章
     * @param index 页码数，最小为1
     * @param max 每页最大数量，最大为100
     * @return List<Article>，文章列表
     */
    @GetMapping("/list")
    public ExactlyResponseEntity<?> multiplyGet(@RequestParam Integer index,
                                                            @RequestParam(defaultValue = "10", required = false) Integer max) {
        // 生成类型
        if (index <= 0) {
            return ExactlyResponseEntity.failUnknown("索引的值无效");
        }
        List<Article> articles = articleService.getArticles(index, max);
        if (articles == null) {
            return ExactlyResponseEntity.failUnknown("指定的页码太大");
        }

        return ExactlyResponseEntity.successWithBody(articles);
    }

    @GetMapping("/search")
    public ExactlyResponseEntity<?> search(@RequestParam String keyword,
                                           @RequestParam(required = false, defaultValue = "1") Integer index,
                                           @RequestParam(required = false, defaultValue = "10") Integer max) {
        max = max > 100 ? 100 : max;
        return ExactlyResponseEntity.successWithBody(articleService.search(keyword, index, max));
    }

}
