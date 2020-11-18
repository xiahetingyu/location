package com.xiahe.tools;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @description 文件操作工具
 * @author: Yue
 * @create: 2019.12.29 21:38
 **/
public class FileTools {

    /**
     * 从流中读取数据转换为字符串
     *
     * @param inputStream 输入流
     * @param charset     字符集
     * @return 文本内容
     */
    public static String toString(InputStream inputStream, Charset charset) {
        //缓冲区
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回结果
        return stringBuilder.toString();
    }

    /**
     * 从流中读取数据转换为字符串
     *
     * @param inputStream 输入流
     * @return 文本内容
     */
    public static String toString(InputStream inputStream) {
        return toString(inputStream, StandardCharsets.UTF_8);
    }

    /**
     * 从文件中读取数据转换为字符串
     *
     * @param file    源文件
     * @param charset 字符集
     * @return 文本内容
     */
    public static String toString(File file, Charset charset) {
        try {
            return toString(new FileInputStream(file), charset);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 从文件中读取数据转换为字符串
     *
     * @param file 源文件
     * @return 文本内容
     */
    public static String toString(File file) {
        return toString(file, StandardCharsets.UTF_8);
    }

}
