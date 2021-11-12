package com.biocar.controller;

import com.biocar.ExactlyResponseEntity;
import com.biocar.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author DeSen Xu
 * @date 2021-11-11 16:19
 */
@RequestMapping("/balance")
@RestController
public class BalanceController {

    private BalanceService balanceService;

    @Autowired
    public void setBalanceService(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("/sum")
    public ExactlyResponseEntity<Double> getBalanceOfTheDay(@RequestParam String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate;
        try {
            parsedDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return ExactlyResponseEntity.create(HttpStatus.BAD_REQUEST.value(), Double.class)
                    .fail("参数有误, 正确参数如: 2021-11-11")
                    .build();
        }
        Double balanceOfTheDay = balanceService.getBalanceOfTheDay(parsedDate);
        if (balanceOfTheDay == null) {
            return ExactlyResponseEntity.create(Double.class)
                    .fail("该日期下没有可用的账单")
                    .build();
        }
        return ExactlyResponseEntity.create(Double.class)
                .data(balanceOfTheDay)
                .build();
    }


}
