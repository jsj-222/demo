package com.biocar.service;

import com.biocar.bean.Article;
import org.springframework.lang.Nullable;

import java.util.List;
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

    /**
     * 文章分页获取
     * @param index 第几页, 最小为0
     * @param maxCount 每页的最大显示数, 最大为100
     * @return 文章列表
     */
    @Nullable
    List<Article> getArticles(int index, int maxCount);

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

    /**
     * 搜索文章
     * @param keyword 文章关键字
     * @param index 显示第几页
     * @param max 每页最大显示数
     * @return 匹配的文章列表
     */
    List<Article> search(String keyword, int index, int max);


}
