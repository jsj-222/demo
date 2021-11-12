package com.biocar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biocar.bean.Balance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author DeSen Xu
 * @date 2021-11-11 14:47
 */
@Mapper
public interface BalanceMapper extends BaseMapper<Balance> {

    String COLUMN_ID = "id";

    String COLUMN_DATE = "date";

    String COLUMN_PROJECT_ID = "project_id";

    String COLUMN_REVENUE = "revenue";

    String COLUMN_MASTER = "master";

    String COLUMN_NOTE = "note";

    String COLUMN_BALANCE_TYPE = "balance_type";

    /**
     * 在查询账单时，项目类型
     */
    String ALIAS_PROJECT_NAME = "projectName";

    /**
     * 根据id获取一天的总账单
     * @param date 日期
     * @return 当天的总收入
     */
    Double getBalanceByOfTheDay(@Param("date") Date date);

    /**
     * 获取一天的收入详细清单
     * @param date 日期
     * @return 收入详细清单, KEY为BalanceMapper中的常量, 但COLUMN_PROJECT_ID不可用, ALIAS_PROJECT_NAME可用
     * @see com.biocar.mapper.BalanceMapper
     */
    List<Map<String, Object>> getTotalBalanceIn(Date date);

    /**
     * 获取一天的支出详细清单
     * @param date 日期
     * @return 支出详细清单, KEY为BalanceMapper中的常量, 但COLUMN_PROJECT_ID不可用, ALIAS_PROJECT_NAME可用
     * @see com.biocar.mapper.BalanceMapper
     */
    List<Map<String, Object>> getTotalBalanceOut(Date date);

}
