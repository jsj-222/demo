package com.biocar;

import com.biocar.bean.CommonResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * 配合CommonResponse来创建响应
 * @see CommonResponse
 * @author DeSen Xu
 * @date 2021-11-04 20:38
 */
public class ExactlyResponseEntity<T> extends ResponseEntity<CommonResponse<T>> {

    public ExactlyResponseEntity(CommonResponse<T> body, MultiValueMap<String, String> headers, int status) {
        super(body, headers, status);
    }

    /**
     * 创建一个响应状态码为200的响应
     * @param type 响应数据data的类型
     */
    public static <R> Builder<R> create(Class<R> type) {
        return new Builder<>(HttpStatus.OK.value());
    }

    /**
     * 自定义响应状态码
     * @param status http状态码
     * @param type 响应数据data的类型
     */
    public static <R> Builder<R> create(int status, Class<R> type) {

        return new Builder<>(status);
    }

    public static class Builder<R> {

        private final int statusCode;

        private String message;

        private int code;

        private R data;

        private final HttpHeaders headers = new HttpHeaders();

        private Builder(int statusCode) {
            this.statusCode = statusCode;
        }

        public Builder<R> code(int code) {
            this.code = code;
            return this;
        }

        public Builder<R> success() {
            return code(0);
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


}
