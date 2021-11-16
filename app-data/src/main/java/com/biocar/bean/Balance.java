package com.biocar.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biocar.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author DeSen Xu
 * @date 2021-11-11 14:18
 */
@Data
@NoArgsConstructor
@TableName("t_balance")
public class Balance {

    /**
     * 账单id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账单创建日期
     */
    @TableField
    @JsonSerialize(using = DateSerializer.class)
    private Date date;

    /**
     * 项目id
     */
    @TableField("project_id")
    private int projectId;

    /**
     * 当日收入/支出
     */
    @TableField
    private BigDecimal revenue;

    /**
     * 负责人
     */
    @TableField
    private String master;

    /**
     * 备注
     */
    @TableField
    private String note;

    /**
     * true表示正收入
     * false表示支出
     */
    @TableField("balance_type")
    private boolean balanceType;

    public Balance(Date date, int projectId, BigDecimal revenue, String master, String note, boolean balanceType) {
        this.date = date;
        this.projectId = projectId;
        this.revenue = revenue;
        this.master = master;
        this.note = note;
        this.balanceType = balanceType;
    }
}
