package com.xiahe.other;

import org.apache.commons.io.FileUtils;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description
 * @author: Yue
 * @create: 2020.11.24 22:57
 **/
public class Main {

    public static void main(String[] args) throws IOException {
        //根路径
        File root = new File("D:\\DataCache\\identity");

        //地区编号
        List<String> lines = FileUtils.readLines(new File("D:\\Development\\GitHub\\location\\doc", "areas.txt"));
        Set<String> areas = new HashSet<>();
        for (String line : lines) {
            String temp = line.split("\t")[0];
            Assert.isTrue(temp.length() == 6, line + "长度错误");
            areas.add(temp);
        }

        //写出文件
        for (String areaString : areas) {
            File area = new File(root, areaString);
            System.out.println("创建目录" + area.getAbsolutePath() + ":" + area.mkdirs());
            for (int i = 1970; i < 2020; i++) {
                File year = new File(area, String.valueOf(i));
                RandomAccessFile randomAccessFile = new RandomAccessFile(year, "rw");
                randomAccessFile.setLength((Year.isLeap(i) ? 366 : 365) * 1000);
                randomAccessFile.close();
                System.out.println("创建文件" + year.getAbsolutePath() + ":" + year.isFile());
            }
        }
    }

}
