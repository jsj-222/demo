package com.biocar.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author DeSen Xu
 * @date 2021-11-11 14:18
 */
@Data
@TableName("t_balance")
public class Balance {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    private Date date;

    @TableField("project_id")
    private String projectId;

    @TableField
    private Double revenue;

    @TableField
    private String master;

    @TableField
    private String note;

    /**
     * true表示正收入
     * false表示支出
     */
    @TableField("balance_type")
    private boolean balanceType;
}
