package com.xiahe;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * @description 手机号工具
 * @author: Yue
 * @create: 2020.02.11 17:30
 **/
public class PhoneTool {

    public static void main(String[] args) throws Exception {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/root/phone.txt")));

        String head = "159";
        String tail = "976";
        for (int i = 100000; i < 200000; i++) {
            StringBuilder stringBuilder = new StringBuilder(head);
            stringBuilder.append(i);
            stringBuilder.deleteCharAt(3);
            stringBuilder.append(tail);
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

}
