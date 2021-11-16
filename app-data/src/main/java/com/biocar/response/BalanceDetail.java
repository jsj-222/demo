package com.biocar.response;

import com.biocar.serializer.BooleanSerializer;
import com.biocar.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用于响应的账单类
 * @author DeSen Xu
 * @date 2021-11-16 21:07
 */
@Data
@NoArgsConstructor
public class BalanceDetail {
    /**
     * 账单id
     */
    private Integer id;

    /**
     * 账单创建日期
     */
    @JsonSerialize(using = DateSerializer.class)
    private Date date;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 当日收入/支出 (正数)
     */
    private Double revenue;

    /**
     * 负责人
     */
    private String master;

    /**
     * 备注
     */
    private String note;

    /**
     * 1表示正收入, 0表示支出
     */
    @JsonSerialize(using = BooleanSerializer.class)
    private boolean balanceType;

}
