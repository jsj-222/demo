package com.biocar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biocar.bean.Balance;
import com.biocar.mapper.BalanceMapper;
import com.biocar.response.BalanceDetail;
import com.biocar.service.BalanceService;
import com.biocar.utils.WrapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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
    public double getBalanceOfTheDay(Date date) {
        Double balanceByOfTheDay = balanceMapper.getBalanceByOfTheDay(date);
        if (balanceByOfTheDay == null) {
            throw new NoSuchElementException();
        } else {
            return balanceByOfTheDay;
        }
    }

    @Override
    public double getBalanceIn(Date date) {
        List<Object> objects = balanceMapper.selectObjs(new QueryWrapper<Balance>()
                .select(WrapperUtils.sum(BalanceMapper.COLUMN_REVENUE))
                .eq(BalanceMapper.COLUMN_BALANCE_TYPE, true)
                .eq(BalanceMapper.COLUMN_DATE, date));
        if (objects.size() == 0) {
            throw new NoSuchElementException();
        }
        return (Double) objects.get(0);
    }

    @Override
    public double getBalanceOut(Date date) {
        List<Object> objects = balanceMapper.selectObjs(new QueryWrapper<Balance>()
                .select(WrapperUtils.sum(BalanceMapper.COLUMN_REVENUE))
                .eq(BalanceMapper.COLUMN_BALANCE_TYPE, false)
                .eq(BalanceMapper.COLUMN_DATE, date));
        if (objects.size() == 0) {
            throw new NoSuchElementException();
        }
        return (Double) objects.get(0);
    }

    @Override
    public List<BalanceDetail> getBalanceInList(Date date) {
        List<BalanceDetail> totalBalanceIn = balanceMapper.getTotalBalanceIn(date);
        if (totalBalanceIn.size() == 0) {
            return null;
        }
        return totalBalanceIn;
    }

    @Override
    public List<BalanceDetail> getBalanceOutList(Date date) {
        List<BalanceDetail> totalBalanceOut = balanceMapper.getTotalBalanceOut(date);
        if (totalBalanceOut.size() == 0) {
            return null;
        }
        return totalBalanceOut;
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
    public BalanceDetail getBalanceById(int id) {
        return balanceMapper.getBalanceById(id);
    }
}
