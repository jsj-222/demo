package com.biocar.utils;

/**
 * @author DeSen Xu
 * @date 2021-11-11 15:51
 */
public class WrapperUtils {

    private WrapperUtils() {}

    /**
     * 构造sum函数
     * @param column 要计算的列
     * @return SUM('column')
     */
    public static String sum(String column) {
        return "SUM(" + column + ")";
    }

    /**
     * 构造sum函数, 并起别名
     * @param column 列名
     * @param alias 别名
     * @return SUM('column') AS 'alias'
     */
    public static String sumAs(String column, String alias)  {
        return sum(column) + "AS" + alias;
    }
}
