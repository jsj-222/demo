package com.biocar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biocar.bean.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author DeSen Xu
 * @date 2021-11-06 22:02
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    String COLUMN_ID = "id";

}
