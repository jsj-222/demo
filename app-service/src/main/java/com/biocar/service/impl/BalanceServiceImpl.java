package com.biocar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biocar.bean.Balance;
import com.biocar.mapper.BalanceMapper;
import com.biocar.service.BalanceService;
import com.biocar.utils.WrapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author DeSen Xu
 * @date 2021-11-11 15:43
 */
@Service
public class BalanceServiceImpl implements BalanceService {

    private BalanceMapper balanceMapper;

    @Autowired
    public void setBalanceMapper(BalanceMapper balanceMapper) {
        this.balanceMapper = balanceMapper;
    }

    @Override
    public Double getBalanceOfTheDay(Date date) {
        return balanceMapper.getBalanceByOfTheDay(date);
    }

    @Override
    public Double getBalanceIn(Date date) {
        List<Object> objects = balanceMapper.selectObjs(new QueryWrapper<Balance>()
                .select(WrapperUtils.sum(BalanceMapper.COLUMN_REVENUE))
                .eq(BalanceMapper.COLUMN_BALANCE_TYPE, true)
                .eq(BalanceMapper.COLUMN_DATE, date));
        if (objects.size() == 0) {
            return null;
        }
        return (Double) objects.get(0);
    }

    @Override
    public Double getBalanceOut(Date date) {
        List<Object> objects = balanceMapper.selectObjs(new QueryWrapper<Balance>()
                .select(WrapperUtils.sum(BalanceMapper.COLUMN_REVENUE))
                .eq(BalanceMapper.COLUMN_BALANCE_TYPE, false)
                .eq(BalanceMapper.COLUMN_DATE, date));
        if (objects.size() == 0) {
            return null;
        }
        return (Double) objects.get(0);
    }

    @Override
    public List<Map<String, Object>> getTotalBalanceIn(Date date) {
        List<Map<String, Object>> totalBalanceIn = balanceMapper.getTotalBalanceIn(date);
        return totalBalanceIn.size() == 0 ? null : totalBalanceIn;
    }

    @Override
    public List<Map<String, Object>> getTotalBalanceOut(Date date) {
        List<Map<String, Object>> totalBalanceOut = balanceMapper.getTotalBalanceOut(date);
        return totalBalanceOut.size() == 0 ? null : totalBalanceOut;
    }

    @Override
    public void addBalance(Balance balance) {
        balanceMapper.insert(balance);
    }

    @Override
    public void deleteBalance(int id) throws NoSuchElementException {
        int i = balanceMapper.deleteById(id);
        if (i == 0) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void updateBalance(Balance balance) throws NoSuchElementException {
        int i = balanceMapper.updateById(balance);
        if (i == 0) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Balance getBalanceById(int id) {
        return balanceMapper.selectById(id);
    }
}
