package com.xiahe.service;

import com.xiahe.repository.IdentityRepository;
import com.xiahe.tools.IdentityTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @description 身份认证服务实现
 * @author: Yue
 * @create: 2020.01.06 01:15
 **/
@Service
public class IdentityServiceImp implements IdentityService {

    private ApplicationContext applicationContext;

    private IdentityRepository identityRepository;

    @Autowired
    public IdentityServiceImp(ApplicationContext applicationContext, IdentityRepository identityRepository) {
        this.applicationContext = applicationContext;
        this.identityRepository = identityRepository;
    }

    @Override
    public void identity(String[] prefix, String start, String end, int sex) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Development\\a.txt")));
            List<String> dates = IdentityTools.dates(start, end);
            List<String> sequences = IdentityTools.sequences(sex);

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
                        stringBuilder.append(IdentityTools.check(stringBuilder));
                        stringBuilder.append("\n");
                        bufferedWriter.write(stringBuilder.toString());
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

}
