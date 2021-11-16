package com.biocar.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biocar.serializer.TimestampDeserializer;
import com.biocar.serializer.TimestampSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author DeSen Xu
 * @date 2021-11-06 22:01
 */
@Data
@TableName("articles")
public class Article {

    /**
     * 文章id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文章标题
     */
    @TableField
    private String title;

    /**
     * 文章类型
     */
    @TableField("article_type")
    private Integer articleType;

    /**
     * 文章内容
     */
    @TableField
    private String body;

    /**
     * 文章来源url
     */
    @TableField("source_url")
    private String sourceUrl;

    /**
     * 文章来源
     */
    @TableField
    private String source;

    /**
     * 文章查看量
     */
    @TableField("view_count")
    private Integer viewCount;

    /**
     * 开始时间, 毫秒级别时间戳
     */
    @TableField("started_at")
    @JsonDeserialize(using = TimestampDeserializer.class)
    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp startedAt;

    /**
     * 权重
     */
    @TableField()
    private BigDecimal weight;

    /**
     * 关键字
     */
    @TableField
    private String keyword;

    /**
     * 是否被激活
     */
    @TableField
    private Integer active;

    /**
     * 是否被删除
     */
    @TableField("is_delete")
    private Integer isDelete;

    /**
     * 创建于, 返回毫秒级别时间戳
     */
    @TableField("created_at")
    @JsonDeserialize(using = TimestampDeserializer.class)
    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp createdAt;

    /**
     * 更新于，返回毫秒级别时间戳
     */
    @TableField("updated_at")
    @JsonDeserialize(using = TimestampDeserializer.class)
    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp updatedAt;

    /**
     * 图片url
     */
    @TableField("img_url")
    private String imgUrl;

}
