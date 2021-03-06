package com.biocar.service.impl;

import com.biocar.bean.Article;
import com.biocar.mapper.ArticleMapper;
import com.biocar.service.ArticleService;
import com.biocar.service.EsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author DeSen Xu
 * @date 2021-11-06 22:02
 */
@Service
public class ArticleServiceImpl implements ArticleService {

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

    @Override
    public Article getArticle(String id) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            throw new NoSuchElementException();
        }
        return article;
    }

    @Override
    public List<Article> getArticles(int index, int maxCount) {
        // mysql分页从0开始, 这里需要减1
        List<Article> articles = articleMapper.selectByPage(index, maxCount);
        if (articles.size() == 0) {
            return null;
        }
        return articles;
    }


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void modifyArticle(Article article) throws NoSuchElementException {
        int i = articleMapper.updateById(article);
        try {
            esArticleService.modifyArticle(String.valueOf(article.getId()), article.getTitle(), article.getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(i==0){
            throw new NoSuchElementException();
        }

    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Long addArticle(Article article) {
        articleMapper.insert(article);
        try {
            esArticleService.addArticle(String.valueOf(article.getId()), article.getTitle(), article.getBody());
        } catch (IOException e) {
            // 用于回滚
            throw new RuntimeException(e);
        }
        return article.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void deleteArticle(int id) throws NoSuchElementException {
       int i= articleMapper.deleteById(id);
        try {
            esArticleService.deleteArticle(String.valueOf(id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(i == 0){
           throw new NoSuchElementException();
       }

    }

    @Override
    public List<Article> search(String keyword, int index, int max) {
        // es分页从0开始
        List<Long> search;
        try {
            search = esArticleService.search(keyword, index - 1, max);
        } catch (IOException e) {
            return Collections.emptyList();
        }
        if (search.size() == 0) {
            return Collections.emptyList();
        }
        return articleMapper.search(concatSearchWhereSql(search));
    }

    /**
     * 拼接搜索时的whereSql语句
     * @param ids id列表
     * @return 类似于: id IN (1,2,3,4)
     */
    private String concatSearchWhereSql(List<Long> ids) {
        // 拼接where语句, 此处不用考虑sql注入
        StringBuilder whereSql = new StringBuilder(ids.size() * 8);
        whereSql.append(ArticleMapper.COLUMN_ID)
                .append(" IN (");
        for (Long id : ids) {
            whereSql.append(id)
                    .append(",");
        }
        // 删除多余逗号以及添加右括号
        whereSql.deleteCharAt(whereSql.length() - 1)
                .append(")");
        return whereSql.toString();
    }


}
