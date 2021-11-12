package com.biocar.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biocar.common.TimestampSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
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

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    private String title;

    @TableField("article_type")
    private Integer articleType;

    @TableField
    private String body;

    @TableField("source_url")
    private String sourceUrl;

    @TableField
    private String source;

    @TableField("view_count")
    private Integer viewCount;

    @TableField("started_at")
    @JsonSerialize(using = TimestampSerializer.class)
    @ApiModelProperty(dataType = "string")
    private Timestamp startedAt;

    @TableField()
    @ApiModelProperty(example = "50.5")
    private BigDecimal weight;

    @TableField
    private String keyword;

    @TableField
    private Integer active;

    @TableField("is_delete")
    private Integer isDelete;

    @TableField("created_at")
    @JsonSerialize(using = TimestampSerializer.class)
    @ApiModelProperty(dataType = "string")
    private Timestamp createdAt;

    @TableField("updated_at")
    @JsonSerialize(using = TimestampSerializer.class)
    @ApiModelProperty(example = "1636694415793", dataType = "java.lang.String", value = "更新时间", name = "test")
    private Timestamp updatedAt;

    @TableField("img_url")
    @ApiModelProperty(example = "1636694415793", dataType = "String", value = "更新时间", name = "test")
    private String imgUrl;

}
