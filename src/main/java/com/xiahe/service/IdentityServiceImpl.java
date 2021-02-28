package com.xiahe.service;

import com.xiahe.util.IdentityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

/**
 * @description 身份信息服务实现
 * @author: Yue
 * @create: 2020.01.06 01:15
 **/
@Service
public class IdentityServiceImpl implements IdentityService {

    @Override
    public void identity(String[] prefix, String start, String end, int sex) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Development\\Cache\\identitys\\411528\\1995")));
            List<String> dates = IdentityUtil.dates(start, end);
            List<String> sequences = IdentityUtil.sequences(sex);

            System.out.println("前缀：" + prefix.length);
            for (String temp : prefix) {
                System.out.println(temp);
            }

            System.out.println("日期区间：" + dates.size());
            for (String date : dates) {
                System.out.println(date);
            }

            System.out.println("顺序位：" + sequences.size());
            for (String sequence : sequences) {
                System.out.println(sequence);
            }

            int count = 0;

            for (String temp : prefix) {
                for (String date : dates) {
                    for (String sequence : sequences) {
                        StringBuilder stringBuilder = new StringBuilder(temp);
                        stringBuilder.append(date);
                        stringBuilder.append(sequence);
                        bufferedWriter.write(IdentityUtil.check(stringBuilder));
                        count++;
                    }
                }
            }

            System.out.println("共计：" + count);

            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String dataFilePathKey = "";

    @Autowired
    private ParameterService parameterService;

    @Override
    public void generateIdentityBlock(String prefix6, String year) {
        //先确定文件
        //根据前缀和年份可以得出所有可能的组合
        //将所有组合的最后一位写出到字节数组中
        //然后写出到文件
        File parent = new File("C:\\Development\\Cache\\identitys", prefix6);
        parent.mkdirs();
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(parent, year)))) {
            bufferedOutputStream.write(generateIdentityBlockBytes(prefix6, year));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] generateIdentityBlockBytes(String prefix, String year) {
        //保存数据结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(366000);

        //获取所有顺序
        List<String> identitySequences = IdentityUtil.getIdentitySequences();

        //获取所有日期
        List<String> oneYearDays = IdentityUtil.getOneYearDays(year);

        //遍历所有可能
        StringBuilder stringBuilder = new StringBuilder(prefix).append(year).append("0000000");

        for (String oneYearDay : oneYearDays) {
            stringBuilder.replace(10, 14, oneYearDay);
            for (String identitySequence : identitySequences) {
                stringBuilder.replace(14, 17, identitySequence);
                byteArrayOutputStream.write(IdentityUtil.checkByte(stringBuilder));
            }
        }

        //返回数据结果
        return byteArrayOutputStream.toByteArray();
    }

    public static void main(String[] args) {
        long currentTimeMillis = System.currentTimeMillis();
        IdentityServiceImpl identityServiceImp = new IdentityServiceImpl();
        for (int i = 1000000; i < 2000000; i++) {
            for (int j = 1921; j <= 2020; j++) {
                identityServiceImp.generateIdentityBlock(String.valueOf(i).substring(1), String.valueOf(j));
            }
            System.out.println(i);
        }

        System.out.println(System.currentTimeMillis() - currentTimeMillis + "ms.");
    }

}
