package com.biocar.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

/**
 * @author DeSen Xu
 * @date 2021-11-11 15:00
 */
@SpringBootTest
public class BalanceMapperTest {

    private BalanceMapper balanceMapper;

    @Autowired
    public void setBalanceMapper(BalanceMapper balanceMapper) {
        this.balanceMapper = balanceMapper;
    }

    @Test
    public void test() {
        System.out.println(balanceMapper.getBalanceByOfTheDay(new Date(121, Calendar.NOVEMBER, 1)));
    }
}
