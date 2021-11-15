package com.biocar;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author DeSen Xu
 * @date 2021-11-14 22:04
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResBean<T> {

    private static final String SUCCESS_CODE = "20000";

    /**
     * 返回code码，20000成功，其他失败
     */
    private String code;

    /**
     * 对code的描述
     */
    private String message = "success";

    /**
     * 返回体
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 成功返回
     */
    public static <T> ResBean<T> success() {
        return new ResBean<>();
    }

    /**
     * 成功返回携带数据体
     * @param data 响应数据
     */
    public static <T> ResBean<T> successWithObj(T data) {
        ResBean<T> resBean = new ResBean<>();
        resBean.setCode(SUCCESS_CODE);
        resBean.setData(data);
        return resBean;
    }

    /**
     * 错误返回携带信息
     * @param errorMsg 错误信息
     */
    public static <T> ResBean<T> failWithMsg(String errorMsg) {
        ResBean<T> resBean = new ResBean<>();
        resBean.setCode("0");
        resBean.setMessage(errorMsg);
        return resBean;
    }

    /**
     * 错误返回携带数据体
     * @param errorCode 错误码
     * @param data 响应数据
     */
    public static <T> ResBean<T> failWithObj(String errorCode, T data) {
        ResBean<T> resBean = new ResBean<>();
        resBean.setCode(errorCode);
        resBean.setMessage("fail");
        resBean.setData(data);
        return resBean;
    }


}
