package com.biocar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biocar.bean.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2021-11-06 22:02
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    String COLUMN_ID = "id";

    String COLUMN_TITLE = "title";


    /**
     * 分页获取文章
     * @param index 当前页码数, 最小为0
     * @param max 每页最多显示多少
     * @return 文章列表
     */
    List<Article> selectByPage(@Param("index") int index, @Param("max") int max);

    /**
     * 搜索文章
     * @param keyword 文章关键字
     * @param index 显示第几页
     * @param max 每页最大显示数
     * @return 匹配的文章列表
     */
    List<Article> search(@Param("keyword") String keyword,@Param("index") int index,@Param("max") int max);
}
