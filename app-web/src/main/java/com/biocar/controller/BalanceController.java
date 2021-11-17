package com.biocar.controller;

import com.biocar.ResBean;
import com.biocar.bean.Balance;
import com.biocar.response.BalanceDetail;
import com.biocar.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 账单接口
 * @menu 账单接口
 * @author DeSen Xu
 * @date 2021-11-11 16:19
 */
@RequestMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@CrossOrigin
public class BalanceController {

    private BalanceService balanceService;

    @Autowired
    public void setBalanceService(BalanceService balanceService) {
        this.balanceService = balanceService;
    }


    /**
     * 将字符串按照 yyyy-MM-dd 的格式解析
     * @param date 字符串日期, 若为null返回当天日期
     * @return Date类日期
     * @throws ParseException 字符串格式有误,解析失败
     */
    private Date parseDate(String date) throws ParseException {
        if (date == null) {
            return new Date();
        }
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(date);
    }

    /**
     * 获取某一天的总收入
     * @status done
     * @param date 日期, 例如: 2020-11-01 <b>不填默认为当天日期</b>
     * @return 当天的总收入, 示例:
     * {
     *     "code": 20000,
     *     "message": "success",
     *     "data": -25741.59
     * }
     */
    @GetMapping("/sum")
    public ResBean<Double> getBalanceOfTheDay(@RequestParam(required = false) String date) {
        try {
            Double balanceOfTheDay = balanceService.getBalanceOfTheDay(parseDate(date));
            return ResBean.successWithObj(balanceOfTheDay);
        } catch (NoSuchElementException e) {
            return ResBean.failWithMsg("没有找到当天的账单");
        } catch (ParseException e) {
            return ResBean.failWithMsg("传入的参数有误");
        }
    }

    /**
     * 添加账单
     * @status done
     * @param date 日期, 例如: 2020-11-01. <b>不填默认为当天日期</b>
     * @param projectId 项目id
     *                  1: 销货收入 2: 其他收入 3: 零食采购 4: 采购支出 5: 房租 6: 工资
     *                  7: 利息支出 8: 办公费 9: 水电 10: 运费 11: 招待 12: 其他
     * @param revenue 当天收入/支出, 不管是收入还是支出, <b>统一提交正数</b>, 在balanceType中声明是支出还是收入
     * @param note 备注
     * @param master 该账单的管理人
     * @param balanceType 账单类型, 0: 支出, 1: 收入 (默认为收入)
     * @return 返回示例:
     * {
     *     "code": 20000,
     *     "message": "success"
     * }
     */
    @PostMapping(value = "/add", headers = "Content-Type=" + MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResBean<Void> addBalance(@RequestParam(required = false) String date,
                                    @RequestParam Integer projectId,
                                    @RequestParam Double revenue,
                                    @RequestParam(required = false) String note,
                                    @RequestParam String master,
                                    @RequestParam(required = false, defaultValue = "1") Integer balanceType) {

        try {
            Balance balance = new Balance(parseDate(date),
                    projectId,
                    BigDecimal.valueOf(revenue),
                    master,
                    note,
                    balanceType != 0);
            balanceService.addBalance(balance);
            return ResBean.success();
        } catch (ParseException e) {
            return ResBean.failWithMsg("传入的参数有误");
        }
    }

    /**
     * 根据id查询账单
     * @param id 账单id
     * @status done
     * @return 返回示例:
     * {
     * 	"code": 20000,
     * 	"message": "success",
     * 	"data": {
     * 		"id": 1,
     * 		"date": "2021-11-01",
     * 		"projectName": "销货收入",
     * 		"revenue": 3600,
     * 		"master": "吴彬",
     * 		"note": "",
     * 		"balanceType": 1
     *        }
     * }
     */
    @GetMapping("/query")
    public ResBean<BalanceDetail> getBalanceById(@RequestParam Integer id) {
        BalanceDetail balance = balanceService.getBalanceById(id);
        if (balance == null) {
            return ResBean.failWithMsg("未找到该账单");
        }
        return ResBean.successWithObj(balance);
    }


    /**
     * 根据id删除账单
     * @status done
     * @param id 账单id
     * @return 返回示例:
     * {
     *     "code": 20000,
     *     "message": "success"
     * }
     */
    @PostMapping(value = "/delete", headers = "Content-Type=" + MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResBean<Void> deleteBalanceById(@RequestParam Integer id) {
        try {
            balanceService.deleteBalance(id);
            return ResBean.success();
        } catch (NoSuchElementException e) {
            return ResBean.failWithMsg("未找到该账单");
        }
    }

    /**
     * 获取某一天的总收入
     * @status done
     * @param date 日期, 如 2021-11-01, <b>不填默认为当天日期</b>
     * @return 总收入, 示例:
     * {
     *     "code": 20000,
     *     "message": "success",
     *     "data": 15768.41
     * }
     */
    @GetMapping("/in")
    public ResBean<Double> getBalanceIn(@RequestParam(required = false) String date) {
        try {
            Double balanceIn = balanceService.getBalanceIn(parseDate(date));
            return ResBean.successWithObj(balanceIn);
        } catch (ParseException e) {
            return ResBean.failWithMsg("传入的参数有误");
        } catch (NoSuchElementException e) {
            return ResBean.failWithMsg("未找到当天的账单");
        }
    }

    /**
     * 获取某一天的总支出
     * @status done
     * @param date 日期, 如 2021-11-01, <b>不填默认为当天日期</b>
     * @return 总支出(正数), 示例:
     * {
     *     "code": 20000,
     *     "message": "success",
     *     "data": 41510.0
     * }
     */
    @GetMapping("/out")
    public ResBean<Double> getBalanceOut(@RequestParam(required = false) String date) {
        try {
            double balanceOut = balanceService.getBalanceOut(parseDate(date));
            return ResBean.successWithObj(balanceOut);
        } catch (ParseException e) {
            return ResBean.failWithMsg("传入的参数有误");
        } catch (NoSuchElementException e) {
            return ResBean.failWithMsg("未找到当天的账单");
        }
    }

    /**
     * 获取某一天的收入账单列表
     * @status done
     * @param date 日期, 如 2021-11-01, <b>不填默认为当天日期</b>
     * @return 收入账单列表, 示例:
     * {
     *     "code": 20000,
     *     "message": "success",
     *     "data": [
     *         {
     *             "id": 1,
     *             "date": "2021-11-01",
     *             "projectName": "销货收入",
     *             "revenue": 3600.0,
     *             "master": "吴彬",
     *             "note": "",
     *             "balanceType": 1
     *         },
     *         {
     *             "id": 2,
     *             "date": "2021-11-01",
     *             "projectName": "其他收入",
     *             "revenue": 500.0,
     *             "master": "刘港",
     *             "note": "",
     *             "balanceType": 1
     *         }
     *     ]
     * }
     */
    @GetMapping("/in/list")
    public ResBean<List<BalanceDetail>> getBalanceInList(@RequestParam(required = false) String date) {
        try {
            List<BalanceDetail> balanceInList = balanceService.getBalanceInList(parseDate(date));
            if (balanceInList == null) {
                return ResBean.failWithMsg("未找到当天的账单");
            }
            return ResBean.successWithObj(balanceInList);
        } catch (ParseException e) {
            return ResBean.failWithMsg("传入的参数有误");
        }
    }

    /**
     * 获取某一天的支出账单列表
     * @status done
     * @param date 日期, 如 2021-11-01, <b>不填默认为当天日期</b>
     * @return 支出账单列表, 示例:
     * {
     *     "code": 20000,
     *     "message": "success",
     *     "data": [
     *         {
     *             "id": 6,
     *             "date": "2021-11-01",
     *             "projectName": "工资",
     *             "revenue": 36910.0,
     *             "master": "刘宇",
     *             "note": "",
     *             "balanceType": 0
     *         },
     *         {
     *             "id": 7,
     *             "date": "2021-11-01",
     *             "projectName": "利息支出",
     *             "revenue": 4600.0,
     *             "master": "万维亨",
     *             "note": "",
     *             "balanceType": 0
     *         }
     *     ]
     * }
     */
    @GetMapping("/out/list")
    public ResBean<List<BalanceDetail>> getBalanceOutList(@RequestParam(required = false) String date) {
        try {
            List<BalanceDetail> balanceOutList = balanceService.getBalanceOutList(parseDate(date));
            if (balanceOutList == null) {
                return ResBean.failWithMsg("未找到当天的账单");
            }
            return ResBean.successWithObj(balanceOutList);
        } catch (ParseException e) {
            return ResBean.failWithMsg("传入的参数有误");
        }
    }

    /**
     * 更新账单
     * @status done
     * @param id 账单id
     * @param projectId 项目id
     *                  1: 销货收入 2: 其他收入 3: 零食采购 4: 采购支出 5: 房租 6: 工资
     *                  7: 利息支出 8: 办公费 9: 水电 10: 运费 11: 招待 12: 其他
     * @param revenue 收入/支出 (正数)
     * @param master 负责人
     * @param note 备注
     * @param balanceType 账单类型, 0: 支出, 1:收入
     * @return 示例:
     * {
     *     "code": 20000,
     *     "message": "success"
     * }
     */
    @PostMapping(value = "/update", headers = "Content-Type=" + MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResBean<Void> updateBalance(@RequestParam Integer id,
                                       @RequestParam(required = false) Integer projectId,
                                       @RequestParam(required = false) Double revenue,
                                       @RequestParam(required = false) String master,
                                       @RequestParam(required = false) String note,
                                       @RequestParam(required = false) Integer balanceType) {
        Balance balance = new Balance();
        balance.setId(id);
        balance.setProjectId(projectId);
        balance.setRevenue(revenue == null ? null : BigDecimal.valueOf(revenue));
        balance.setMaster(master);
        balance.setNote(note);
        if (balanceType != null) {
            balance.setBalanceType(balanceType == 1);
        }
        try {
            balanceService.updateBalance(balance);
            return ResBean.success();
        } catch (NoSuchElementException e) {
            return ResBean.failWithMsg("无法找到该账单");
        } catch (IllegalArgumentException e) {
            return ResBean.failWithMsg("projectId范围有误, 请检查后重新输入");
        }
    }




}
