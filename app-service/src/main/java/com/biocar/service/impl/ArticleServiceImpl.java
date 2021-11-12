package com.biocar.service.impl;

import com.biocar.bean.Article;
import com.biocar.mapper.ArticleMapper;
import com.biocar.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
