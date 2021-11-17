package com.biocar.controller;

import com.biocar.ResBean;
import com.biocar.bean.Article;
import com.biocar.enums.ArticleParamSizeLimiter;
import com.biocar.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/article")
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 根据id查询文章
     * @status done
     * @param id 文章id
     * @return 文章信息, 示例:
     * {
     *     "code": 20000,
     *     "message": "success",
     *     "data": {
     *         "id": 1,
     *         "title": "PD-L1/CTLA-4双抗KN046注册临床IND获批准，用于PD-(L)1治疗后进展的非小细胞肺癌",
     *         "articleType": 0,
     *         "body": "<p>hello world</p>",
     *         "sourceUrl": "https://www.wuxuwang.com/zixun/78605309-1c13-11ec-b564-b8599fb66350",
     *         "source": "戊戌数据",
     *         "viewCount": 0,
     *         "startedAt": "1636290385000",
     *         "weight": 5.55,
     *         "keyword": "",
     *         "active": 0,
     *         "isDelete": 0,
     *         "createdAt": "1632844800000",
     *         "updatedAt": "1632844800000",
     *         "imgUrl": "https://file.wuxuwang.com/news/1632390728520-1629786207311-2021-08-24_142304.png"
     *     }
     * }
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
     * @status done
     * @param id 文章id
     * @return 示例:
     * {
     *     "code": 20000,
     *     "message": "success"
     * }
     */
    @PostMapping("/delete")
    public ResBean<Void> deleteArticle(@RequestParam Integer id) {
        try {
            articleService.deleteArticle(id);
            return ResBean.success();
        } catch (NoSuchElementException e) {
            return ResBean.failWithMsg("该文章不存在");
        }
    }

    /**
     * 添加文章
     * @status done
     * @param title 文章标题
     * @param body 文章内容
     * @param source 文章来源
     * @param weight 文章权重
     * @param keyword 文章关键字
     * @param imgUrl 图片url
     * @param sourceUrl 文章来源url
     * @param articleType 文章类型
     * @return 添加成功后，文章的id, 示例:
     * {
     *     "code": 20000,
     *     "message": "success",
     *     "data": 335
     * }
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
     * @status done
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
     * @return 示例:
     * {
     *     "code": 20000,
     *     "message": "success"
     * }
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
     * @status done
     * @param index 页码数，最小为1
     * @param max 每页最大数量，最大为100
     * @return List<Article>，文章列表, 示例:
     * {
     *     "code": 20000,
     *     "message": "success",
     *     "data": [
     *         {
     *             "id": 1,
     *             "title": "PD-L1/CTLA-4双抗KN046注册临床IND获批准，用于PD-(L)1治疗后进展的非小细胞肺癌",
     *             "articleType": 0,
     *             "body": "hello world",
     *             "sourceUrl": "https://www.wuxuwang.com/zixun/78605309-1c13-11ec-b564-b8599fb66350",
     *             "source": "戊戌数据",
     *             "viewCount": 0,
     *             "startedAt": "1636290385000",
     *             "weight": 5.55,
     *             "keyword": "",
     *             "active": 0,
     *             "isDelete": 0,
     *             "createdAt": "1632844800000",
     *             "updatedAt": "1632844800000",
     *             "imgUrl": "https://file.wuxuwang.com/news/1632390728520-1629786207311-2021-08-24_142304.png"
     *         },
     *         {
     *             "id": 2,
     *             "title": "连投三家服务机构，加码互联网医疗，字节跳动的医疗梦进展几何？",
     *             "articleType": 1,
     *             "body": "<div id=\"kr60f993_article\"><p>字节跳动在医疗健康领域的布局正在提速。</p><p><strong>在近期，字节跳动的三笔投资浮出水面</strong>",
     *             "sourceUrl": "https://www.wuxuwang.com/zixun/c90e5433-151d-11ec-b564-b8599fb66350",
     *             "source": "戊戌数据",
     *             "viewCount": 0,
     *             "startedAt": "1631548800000",
     *             "weight": 50.00,
     *             "keyword": "医院,专科医院,门诊部,疫苗,疾病,肿瘤,乳腺癌,肺癌",
     *             "active": 1,
     *             "isDelete": 0,
     *             "createdAt": "1632844800000",
     *             "updatedAt": "1632844800000",
     *             "imgUrl": "https://cdn.vcbeat.top/upload/source/26/82/42/20/5e5cce7f64bf0.jpg"
     *         }
     *     ]
     * }
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
     * @status done
     * @param keyword 文章关键字
     * @param index 页码数, 最小为1
     * @param max 一页最大的显示量, 最大为100
     * @return 文章列表, 搜索结果根据搜索关键字排序, 示例:
     * {
     *     "code": 20000,
     *     "message": "success",
     *     "data": [
     *         {
     *             "id": 92,
     *             "title": "丽珠集团重组新型冠状病毒融合蛋白疫苗进入Ⅲ期临床试验",
     *             "articleType": 1,
     *             "body": "xxxxxxxxxxxxxxxxxxxx"
     *             "sourceUrl": "https://www.pharnexcloud.com/zixun/sx_3656",
     *             "source": "药融云",
     *             "viewCount": 185,
     *             "startedAt": "1630080000000",
     *             "weight": 50.00,
     *             "keyword": "新冠病毒,新型冠状病毒,丽珠集团",
     *             "active": 1,
     *             "isDelete": 0,
     *             "createdAt": "1632844800000",
     *             "updatedAt": "1632844800000",
     *             "imgUrl": "https://yaorongyun-public.oss-cn-shanghai.aliyuncs.com/images/covers/202108/27/729210fe066e84df0f373480913fc104.png"
     *         },
     *         {
     *             "id": 207,
     *             "title": "全球疫苗竞赛烽烟四起，新锐丽珠新冠疫苗临床进展显著",
     *             "articleType": 1,
     *             "body": "<p>进入2021年第二季度以来，全球新冠疫情形势再度升级，新增病例及死亡病例再创新高</p>"
     *             "sourceUrl": "http://www.pharnex.com/show-20-2756-1.html",
     *             "source": "药融圈",
     *             "viewCount": 0,
     *             "startedAt": "1621958400000",
     *             "weight": 50.00,
     *             "keyword": "",
     *             "active": 1,
     *             "isDelete": 0,
     *             "createdAt": "1632844800000",
     *             "updatedAt": "1632844800000",
     *             "imgUrl": ""
     *         }
     *     ]
     * }
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
