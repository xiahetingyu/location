package com.xiahe.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @description 索引位置查找工具
 * @author: Yue
 * @create: 2020.11.22 07:08
 **/
@Component
public class IndexSearchUtilImpl {

    /**
     * 索引位置查找
     *
     * @param identity 身份证号
     * @return 位置索引
     */
    public int index(String identity) {
        //十八位则取其中的十一位
        identity = identity.length() == 18 ? identity.substring(6) : identity;

        //取其中的年月日和顺序码
        return index(identity.substring(0, 4), identity.substring(4, 6), identity.substring(6, 8), identity.substring(8, 11));
    }

    /**
     * 闰年日期
     */
    private List<Integer> leapYearDays = Arrays.asList(0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335);

    /**
     * 平年日期
     */
    private List<Integer> averageYearDays = Arrays.asList(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334);

    /**
     * 索引位置查找
     *
     * @param year     所属年份
     * @param month    所属月份
     * @param day      所属日期
     * @param sequence 所属顺序
     * @return 位置索引
     */
    public int index(int year, int month, int day, int sequence) {
        //判断闰年平年
        List<Integer> yearDays = isLeapYear(year) ? leapYearDays : averageYearDays;

        //获取月份的位置
        int index = yearDays.get(month - 1);

        //获取日期的位置
        index += day - 1;

        //获取顺序的位置
        index = index * 1000 + sequence;

        //返回所在索引
        return index;
    }

    /**
     * 索引位置查找
     *
     * @param year     所属年份
     * @param month    所属月份
     * @param day      所属日期
     * @param sequence 所属顺序
     * @return 位置索引
     */
    public int index(String year, String month, String day, String sequence) {
        return index(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day), Integer.valueOf(sequence));
    }

    /**
     * 是否为闰年
     *
     * @param year 年份
     * @return true 闰年  false平年
     */
    private boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

}
