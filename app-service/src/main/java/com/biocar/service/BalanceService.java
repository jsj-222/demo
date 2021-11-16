package com.biocar.service;

import com.biocar.bean.Balance;
import com.biocar.response.BalanceDetail;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author DeSen Xu
 * @date 2021-11-11 15:43
 */
public interface BalanceService {


    /**
     * 获取某一天的净收入
     * @param date 日期
     * @return 净收入, 若没找到返回null
     * @throws NoSuchElementException 没有找到当天对应的账单
     */
    double getBalanceOfTheDay(Date date) throws NoSuchElementException;


    /**
     * 获取某一天的总收入
     * @param date 日期
     * @return 总收入
     * @throws NoSuchElementException 没有找到当天对应的账单
     */
    double getBalanceIn(Date date) throws NoSuchElementException;

    /**
     * 获取某一天的总支出
     * @param date 日期
     * @return 总支出
     * @throws NoSuchElementException 没有找到当天对应的账单
     */
    double getBalanceOut(Date date) throws NoSuchElementException;

    /**
     * 获取一天的收入详细清单
     * @param date 日期
     * @return 收入详细清单, KEY为BalanceMapper中的常量, 但COLUMN_PROJECT_ID不可用, ALIAS_PROJECT_NAME可用
     * @see com.biocar.mapper.BalanceMapper
     */
    @Nullable
    List<BalanceDetail> getBalanceInList(Date date);

    /**
     * 获取一天的支出详细清单
     * @param date 日期
     * @return 支出详细清单, KEY为BalanceMapper中的常量, 但COLUMN_PROJECT_ID不可用, ALIAS_PROJECT_NAME可用
     * @see com.biocar.mapper.BalanceMapper
     */
    @Nullable
    List<BalanceDetail> getBalanceOutList(Date date);

    /**
     * 添加一个账单
     * @param balance 账单
     */
    void addBalance(Balance balance);

    /**
     * 根据id删除账单
     * @param id 账单id
     * @throws NoSuchElementException 没有找到该账单
     */
    void deleteBalance(int id) throws NoSuchElementException;

    /**
     * 更新账单
     * @param balance 账单信息
     * @throws NoSuchElementException 没有找到该账单
     */
    void updateBalance(Balance balance) throws NoSuchElementException;

    /**
     * 通过id获取账单
     * @param id 账单id
     * @return 账单信息
     */
    @Nullable
    BalanceDetail getBalanceById(int id);
}
