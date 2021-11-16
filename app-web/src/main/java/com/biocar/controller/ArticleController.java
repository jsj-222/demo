package com.biocar.controller;

import com.biocar.ResBean;
import com.biocar.bean.Article;
import com.biocar.enums.ArticleParamSizeLimiter;
import com.biocar.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 文章接口
 * @author DeSen Xu
 * @menu 文章接口
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
     * 根据id查询文章
     * @param id 文章id
     * @return 文章信息
     */
    @GetMapping("/query")
    public ResBean<Article> queryArticle(@RequestParam String id) {
        try {
            Article article = articleService.getArticle(id);
            return ResBean.successWithObj(article);
        } catch (NoSuchElementException e) {
            return ResBean.failWithMsg("没有找到指定的文章");
        }
    }

    /**
     * 删除文章
     * @param id 文章id
     */
    @PostMapping("/delete")
    public ResBean<Void> deleteArticle(@RequestParam String id) {
        try {
            articleService.deleteArticle(id);
            return ResBean.successWithObj(null);
        } catch (NoSuchElementException e) {
            return ResBean.failWithMsg("该文章不存在");
        }
    }

    /**
     * 添加文章
     * @param title 文章标题
     * @param body 文章内容
     * @param source 文章来源
     * @param weight 文章权重
     * @param keyword 文章关键字
     * @param imgUrl 图片url
     * @param sourceUrl 文章来源url
     * @param articleType 文章类型
     * @return 添加成功后，文章的id
     */
    @PostMapping("/insert")
    public ResBean<Long> addArticle(@RequestParam String title,
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

        Long id = articleService.addArticle(article);
        return ResBean.successWithObj(id);

    }


    /**
     * 修改文章
     * @param id 文章id
     * @param title 文章标题
     * @param articleType 文章类型
     * @param body 文章内容
     * @param sourceUrl 文章来源url
     * @param source 文章来源
     * @param weight 文章权重
     * @param keyword 文章关键字
     * @param isDelete 是否被删除
     * @param imgUrl 文章图片url
     */
    @PostMapping("/modify")
    public ResBean<Void> updateArticle(@RequestParam Long id,
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
        article.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
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
            return ResBean.failWithMsg("没有找到目标文章");
        }
        return ResBean.success();
    }

    /**
     * 分页获取文章
     * @param index 页码数，最小为1
     * @param max 每页最大数量，最大为100
     * @return List<Article>，文章列表
     */
    @GetMapping("/list")
    public ResBean<List<Article>> multiplyGet(@RequestParam Integer index,
                                                            @RequestParam(defaultValue = "10", required = false) Integer max) {
        if (ArticleParamSizeLimiter.SEARCH_SIZE_LIMIT.isUnsatisfied(max)) {
            return ResBean.failWithMsg("每页的显示量不符合要求");
        }
        if (ArticleParamSizeLimiter.SEARCH_INDEX_LIMIT.isUnsatisfied(index)) {
            return ResBean.failWithMsg("给定的索引值有误");
        }
        List<Article> articles = articleService.getArticles(index, max);
        if (articles == null) {
            return ResBean.failWithMsg("指定的页码太大");
        }

        return ResBean.successWithObj(articles);
    }

    /**
     * 搜索文章
     * @param keyword 文章关键字
     * @param index 页码数, 最小为0
     * @param max 一页最大的显示量, 最大为100
     * @return 文章列表, 搜索结果根据搜索关键字排序
     */
    @GetMapping("/search")
    public ResBean<List<Article>> search(@RequestParam String keyword,
                                           @RequestParam(required = false, defaultValue = "0") Integer index,
                                           @RequestParam(required = false, defaultValue = "10") Integer max) {
        if (ArticleParamSizeLimiter.SEARCH_SIZE_LIMIT.isUnsatisfied(max)) {
            return ResBean.failWithMsg("每页的显示量不符合要求");
        }
        if (ArticleParamSizeLimiter.SEARCH_INDEX_LIMIT.isUnsatisfied(index)) {
            return ResBean.failWithMsg("给定的索引值有误");
        }
        return ResBean.successWithObj(articleService.search(keyword, index, max));
    }

}
