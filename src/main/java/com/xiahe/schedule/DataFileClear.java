package com.xiahe.schedule;

import com.xiahe.config.CleanConfig;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class DataFileClear {

    @Scheduled(fixedDelay = 10000)
    public void clean() throws IOException {
        //清理垃圾文件的目的是保持数据目录是干净的之前的历史数据都会被删除掉
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss"));

        //先检查目录是否多余
        List<File> listFile = Arrays.asList(Optional.ofNullable(CleanConfig.root.listFiles()).orElse(new File[0]));
        listFile = new ArrayList<>(listFile);
        List<File> except = new ArrayList<>();

        //回收站
        File rubbish = new File(CleanConfig.root, "rubbish");
        if (rubbish.isFile()) {
            FileUtils.forceDelete(rubbish);
        }
        except.add(rubbish);
        rubbish = new File(rubbish, currentTime);
        if (rubbish.isFile()) {
            FileUtils.forceDelete(rubbish);
        }

        //地区编号
        List<String> lines = FileUtils.readLines(CleanConfig.areaFile);
        Set<String> areas = new HashSet<>();
        for (String line : lines) {
            String temp = line.split("\t")[0];
            Assert.isTrue(temp.length() == 6, line + "长度错误");
            areas.add(temp);
        }

        for (String area : areas) {
            File file = new File(CleanConfig.root, area);
            if (file.isFile()) {
                moveToRubbish(file, rubbish);
            }
            except.add(file);
        }
        listFile.removeAll(except);

        //删除多余的文件
        for (File file : listFile) {
            moveToRubbish(file, rubbish);
        }

        //将垃圾桶排除
        except.remove(rubbish.getParentFile());

        //依次检查所有文件夹下的文件是否符合年份
        for (File file : except) {
            List<File> listYearFile = Arrays.asList(Optional.ofNullable(file.listFiles()).orElse(new File[0]));
            listYearFile = new ArrayList<>(listYearFile);
            List<File> expectYearsFile = new ArrayList<>();
            for (int i = CleanConfig.startYear; i <= CleanConfig.endYear; i++) {
                File temp = new File(file, (String.valueOf(i)));
                if (temp.isDirectory()) {
                    moveToRubbish(temp, rubbish);
                }
                expectYearsFile.add(temp);
            }

            //检查是否都是期望的文件
            listYearFile.removeAll(expectYearsFile);

            //删除多余的文件
            for (File temp : listYearFile) {
                moveToRubbish(temp, rubbish);
            }
        }
    }

    /**
     * 移动到垃圾桶
     *
     * @param currentFile 当前文件
     * @param rubbish     垃圾桶
     */
    public void moveToRubbish(File currentFile, File rubbish) throws IOException {
        //确定在垃圾桶内的位置
        String absolutePath = currentFile.getAbsolutePath();
        File file = new File(absolutePath.replace(CleanConfig.root.getAbsolutePath(), rubbish.getAbsolutePath()));

        System.out.println(absolutePath + "移动文件到垃圾桶:" + file.getAbsolutePath());
        //开始移动到垃圾桶
        FileUtils.moveToDirectory(currentFile, file.getParentFile(), true);
    }

    public static void main(String[] args) throws IOException {
        new DataFileClear().clean();
    }

}
