package com.biocar.enums;

/**
 * 用于检测article
 * @author DeSen Xu
 * @date 2021-11-16 13:08
 */
public enum ArticleParamSizeLimiter {
    /**
     * 分页时, 限制每页最大显示的数量
     */
    SEARCH_SIZE_LIMIT(1, 100),
    /**
     * 分页时, 所选页码数的限制
     */
    SEARCH_INDEX_LIMIT(0, Integer.MAX_VALUE)
    ;

    /**
     * 最小值, 包括该值
     */
    private final int min;

    /**
     * 最大值, 不包括该值
     */
    private final int max;

    ArticleParamSizeLimiter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    /**
     * 判断一个数据的长度是否不符合要求
     * @param value 要判断的数据
     * @return 返回true表示该数据不符合要求
     */
    public boolean isUnsatisfied(String value) {
        int len = value.length();
        return len < min || len >= max;
    }

    /**
     * 判断一个值是否在规定的范围内
     * @param value 要判断值
     * @return 返回true表示不在规定的范围内
     */
    public boolean isUnsatisfied(int value) {
        return value < min || value >= max;
    }
}
