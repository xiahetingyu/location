package com.xiahe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description 身份信息工具
 * @author: Yue
 * @create: 2020.01.29 23:32
 **/
public class IdentityUtil {

    /**
     * 获取某年中的所有日期
     *
     * @param year 年份
     * @return 年份中的日期
     */
    public static List<String> getOneYearDays(String year) {
        return isLeapYear(Integer.valueOf(year)) ? leapYearDaysCache : averageYearDaysCache;
    }

    /**
     * 平年日期缓存
     */
    private static List<String> averageYearDaysCache = new ArrayList<>();

    /**
     * 闰年日期缓存
     */
    private static List<String> leapYearDaysCache = new ArrayList<>();

    static {
        //初始化年份日期缓存
        initYearDaysCache();
    }

    /**
     * 初始化年份日期缓存
     */
    private static void initYearDaysCache() {
        //所有可能月份
        String[] allMonths = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        int[] monthDays = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        //所有可能日期
        String[] allDays = new String[31];
        for (int i = 101; i < 132; i++) {
            allDays[i - 101] = String.valueOf(i).substring(1);
        }

        //平年的所有日期
        for (int i = 0; i < allMonths.length; i++) {
            for (int j = 0; j < monthDays[i]; j++) {
                averageYearDaysCache.add(allMonths[i] + allDays[j]);
            }
        }

        //闰年的所有日期
        leapYearDaysCache.addAll(averageYearDaysCache);
        leapYearDaysCache.add(59, "0229");
    }

    /**
     * 是否为闰年
     *
     * @param year 年份
     * @return true 闰年  false平年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 获取身份信息顺序位
     *
     * @return 所有可能的顺序位
     */
    public static List<String> getIdentitySequences() {
        List<String> sequences = new ArrayList<>();
        for (int i = 1000; i < 2000; i++) {
            sequences.add(String.valueOf(i).substring(1));
        }
        return sequences;
    }

    /**
     * 获取身份信息校验位
     *
     * @param prefix17 待补全身份证号
     * @return 校验位结果
     */
    public static int checkByte(StringBuilder prefix17) {
        //相乘并相加
        int sum = 0;
        for (int i = 0; i < coefficient.length; i++) {
            sum += (prefix17.charAt(i) - 48) * coefficient[i];
        }

        //返回校验位
        return (12 - sum % 11) % 11;
    }

    //---------------------------------日期位---------------------------------------------

    /**
     * 获取身份证号指定区间日期
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 区间日期
     */
    public static List<String> dates(String start, String end) {
        List<String> dates = new ArrayList<>();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            long startLong = simpleDateFormat.parse(start).getTime();
            long endLong = simpleDateFormat.parse(end).getTime();
            for (long startDate = startLong; startDate <= endLong; startDate += 86400000L) {
                date.setTime(startDate);
                dates.add(simpleDateFormat.format(date));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

    //---------------------------------顺序位---------------------------------------------

    /**
     * 获取身份证号顺序位
     *
     * @param sex 性别 0不区分 1男 2女
     * @return 顺序位
     */
    public static List<String> sequences(int sex) {
        List<String> sequences = new ArrayList<>();
        for (int i = sex == 1 ? 1 : 0; i < 1000; i += sex == 0 ? 1 : 2) {
            String temp = "000" + i;
            sequences.add(temp.substring(temp.length() - 3));
        }
        return sequences;
    }

    //---------------------------------校验位---------------------------------------------

    /**
     * 获取身份证号校验位
     *
     * @param prefix17 待补全身份证号
     * @return 校验位
     */
    public static char check(StringBuilder prefix17) {
        //相乘并相加
        int sum = 0;
        for (int i = 0; i < coefficient.length; i++) {
            sum += (prefix17.charAt(i) - 48) * coefficient[i];
        }

        //返回校验位
        int check = (12 - sum % 11) % 11;
        return (char) (check + (check == 10 ? 78 : 48));
    }

    /**
     * 身份证校验位权重系数
     */
    private static int[] coefficient = weights();

    /**
     * 身份号证权重系数
     * Mod11-2
     *
     * @return 权重系数
     */
    private static int[] weights() {
        //身份证号前十七位权重系数
        int[] weights = new int[17];
        int temp = 1 << 18;

        //开始运算
        for (int i = 0; i < weights.length; i++) {
            temp = temp >> 1;
            weights[i] = temp % 11;
        }
        return weights;
    }

}
