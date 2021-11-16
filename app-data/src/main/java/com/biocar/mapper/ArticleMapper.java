package com.biocar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biocar.bean.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author DeSen Xu
 * @date 2021-11-06 22:02
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    String COLUMN_ID = "id";

    String COLUMN_TITLE = "title";

    String COLUMN_BODY = "body";

    /**
     * 分页获取文章
     * @param index 当前页码数, 最小为0
     * @param max 每页最多显示多少
     * @return 文章列表
     */
    List<Article> selectByPage(@Param("index") int index, @Param("max") int max);

    /**
     * 根据id获取多篇文章
     * 由于使用foreach具有性能问题, 需要自己拼接sql
     * 详细可见: https://cloud.tencent.com/developer/article/1632378
     * @param whereSql 多篇文章的查询sql
     *                 示例: id IN (1, 2, 3, 4)
     * @return 匹配的文章列表, 使用
     */
    List<Article> search(@Param("whereSql") String whereSql);
}
