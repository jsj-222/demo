package com.biocar;

import com.biocar.bean.CommonResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * 配合CommonResponse来创建响应
 * @see ResBean
 * @author DeSen Xu
 * @date 2021-11-04 20:38
 */
@Deprecated
public class ExactlyResponseEntity<T> extends ResponseEntity<CommonResponse<T>> {

    public ExactlyResponseEntity(CommonResponse<T> body, MultiValueMap<String, String> headers, int status) {
        super(body, headers, status);
    }

    /**
     * 创建一个响应状态码为200的响应
     * @param type 响应数据data的类型
     */
    public static <R> Builder<R> create(Class<R> type) {
        return create(HttpStatus.OK.value(), type);
    }

    /**
     * 自定义响应状态码
     * @param status http状态码
     * @param type 响应数据data的类型
     */
    public static <R> Builder<R> create(int status, Class<R> type) {
        return new Builder<>(status);
    }

    public static <R> Builder<R> create(R data) {
        return new Builder<>(HttpStatus.OK.value(), data);
    }

    public static class Builder<R> {

        private final int statusCode;

        private String message;

        private int code = 20000;

        private R data;

        private final HttpHeaders headers = new HttpHeaders();

        private Builder(int statusCode) {
            this.statusCode = statusCode;
        }

        public Builder(int statusCode, R data) {
            this.statusCode = statusCode;
            this.data = data;
        }
        public Builder<R> code(int code) {
            this.code = code;
            return this;
        }


        public Builder<R> fail() {
            return fail("服务器繁忙,请稍后再试");
        }

        public Builder<R> fail(String message) {
            this.message = message;
            return code(1);
        }


        public Builder<R> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<R> header(String headerName, String headerValue) {
            headers.add(headerName, headerValue);
            return this;
        }

        public Builder<R> data(R data) {
            this.data = data;
            return this;
        }

        public ExactlyResponseEntity<R> build() {
            if (message == null) {
                message = "success";
            }
            return new ExactlyResponseEntity<>(new CommonResponse<>(code, message, data), headers, statusCode);
        }
    }

    public static ExactlyResponseEntity<Void> emptyBodySuccess() {
        return ExactlyResponseEntity.create(Void.class).build();
    }

    public static ExactlyResponseEntity<Void> emptyBodyFail(String message) {
        return ExactlyResponseEntity.create(Void.class).fail(message).build();
    }

    public static <TP> ExactlyResponseEntity<TP> successWithBody(TP data) {
        return ExactlyResponseEntity.create(data).build();
    }
    /**
     * 返回失败响应，但无法确定data类型
     * @param message message信息
     * @return ExactlyResponseEntity<Object>
     */
    public static ExactlyResponseEntity<Object> failUnknown(String message) {
        return ExactlyResponseEntity.create(HttpStatus.OK.value(), Object.class).fail(message).build();
    }


}
