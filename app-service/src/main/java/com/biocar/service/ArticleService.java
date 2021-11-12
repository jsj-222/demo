package com.biocar.service;

import com.biocar.bean.Article;
import org.springframework.lang.Nullable;

import java.util.NoSuchElementException;

/**
 * @author DeSen Xu
 * @date 2021-11-06 22:02
 */
public interface ArticleService {

    /**
     * 获取文章
     * @param id 文章id
     * @return 文章信息
     * @throws NoSuchElementException 没有找到目标文章
     */
    @Nullable
    Article getArticle(String id) throws NoSuchElementException;

    // 添加增删改查接口并实现

    /**
     * 修改文章信息
     * @param article 文章信息
     * @throws NoSuchElementException 没有找到目标文章
     */
    void modifyArticle(Article article) throws NoSuchElementException;

    /**
     * 添加一篇文章
     * @param article 文章信息
     * @return 新添文章的id
     */
    int addArticle(Article article);

    /**
     * 删除文章
     * @param id 文章id
     * @throws NoSuchElementException 没有找到目标文章
     */
    void deleteArticle(String id) throws NoSuchElementException;


}
