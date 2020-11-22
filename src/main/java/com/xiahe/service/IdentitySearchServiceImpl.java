package com.xiahe.service;

import com.xiahe.util.IdentityAnalysisUtil;
import com.xiahe.util.IndexSearchUtilImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @description 身份证号查询服务者实现
 * @author: Yue
 * @create: 2020.11.22 08:13
 **/
@Component
public class IdentitySearchServiceImpl implements IdentitySearchService {

    @Autowired
    private IdentityAnalysisUtil identityAnalysisUtil;

    @Autowired
    private IndexSearchUtilImpl indexSearchUtil;

    @Override
    public Object search(String identity) {
        File identityBelongFile = identityAnalysisUtil.getIdentityBelongFile(identity);

        //有对应的文件
        if (identityBelongFile.isFile()) {
            //获取索引所在位置
            int index = indexSearchUtil.index(identity);

            //读取所在位置信息
            try {
                return getContent(identityBelongFile, index);
            } catch (IOException e) {
                e.printStackTrace();
                return "读取异常" + e.getMessage();
            }

        }

        return "没有这个数据";
    }

    private String getContent(File identityBelongFile, int index) throws IOException {
        byte[] result = new byte[2];

        RandomAccessFile randomAccessFile = new RandomAccessFile(identityBelongFile, "r");
        randomAccessFile.seek(index * 2);
        int read = randomAccessFile.read(result);
        randomAccessFile.close();
        Assert.isTrue(read == result.length, "读取失败");
        return new String(result);
    }

}
