package com.biocar.service;

import java.io.IOException;
import java.util.List;

/**
 * @author DeSen Xu
 * @date 2021-11-15 21:04
 */
public interface EsArticleService {

    /**
     * 全文搜索文章
     * @param keyword 关键字
     * @param index 显示第几页内容, 最小为1
     * @param max 每页最多显示多少列
     * @return 搜索到的文章id
     * @throws IOException 连接服务器失败
     */
    List<Long> search(String keyword, int index, int max) throws IOException;

    /**
     * 添加文章
     * @param id 文章id
     * @param title 文章标题
     * @param body 文章内容
     * @throws IOException 连接服务器失败
     */
    void addArticle(String id, String title, String body) throws IOException;
}
