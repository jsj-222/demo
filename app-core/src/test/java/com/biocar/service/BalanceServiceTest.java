package com.biocar.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author DeSen Xu
 * @date 2021-11-16 20:02
 */
@SpringBootTest
public class BalanceServiceTest {

    private BalanceService balanceService;

    @Autowired
    public void setBalanceService(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Test
    public void test() {
        System.out.println(balanceService.getBalanceOfTheDay(new Date()));
    }
}
