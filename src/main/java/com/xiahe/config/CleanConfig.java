package com.xiahe.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Year;

@Component
public class CleanConfig {

    //数据库根路径
    public static File root = new File("D:\\DataCache\\identity");

    //地区文件
    public static File areaFile = new File("D:\\Development\\GitHub\\location\\doc", "areas.txt");

    //结束年份
    public static int endYear = Year.now().minusYears(1).getValue();

    //起始年份
    public static int startYear = Year.of(endYear).minusYears(49).getValue();

    //自动更新年份
    @Scheduled(cron = "0 0 0 0 0 ? *")
    public void updateYear() {
        endYear = Year.now().minusYears(1).getValue();
        startYear = Year.of(endYear).minusYears(49).getValue();
    }

}
