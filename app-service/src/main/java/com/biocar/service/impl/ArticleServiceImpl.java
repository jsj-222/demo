package com.biocar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biocar.bean.Article;
import com.biocar.mapper.ArticleMapper;
import com.biocar.service.ArticleService;
import com.biocar.utils.WrapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author DeSen Xu
 * @date 2021-11-06 22:02
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleMapper articleMapper;

    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public Article getArticle(String id) {
        return articleMapper.selectById(id);
    }

    @Override
    public List<Article> getArticles(int index, int maxCount) {
        if (index < 0) {
            throw new IllegalArgumentException("index can not less than 0");
        }
        if (maxCount > 100) {
            throw new IllegalArgumentException("article count can not more than 100");
        }
        List<Article> articles = articleMapper.selectByPage(index, maxCount);
        if (articles.size() == 0) {
            return null;
        }
        return articles;
    }


    @Override
    public void modifyArticle(Article article) throws NoSuchElementException {
        int i = articleMapper.updateById(article);
        if(i==0){
            throw new NoSuchElementException();
        }

    }

    @Override
    public int addArticle(Article article) {
        articleMapper.insert(article);
        return article.getId();
    }

    @Override
    public void deleteArticle(String id) throws NoSuchElementException {
       int i= articleMapper.deleteById(id);
       if(i==0){
           throw new NoSuchElementException();
       }

    }

    @Override
    public List<Article> search(String keyword, int index, int max) {
        return articleMapper.search(keyword, index, max);
    }
}
