package com.xiahe.util;

import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @description 身份证号分析工具
 * @author: Yue
 * @create: 2020.11.22 08:23
 **/
@Component
public class IdentityAnalysisUtil {

    public String getIdentityArea(String identity) {
        return identity.substring(0, 6);
    }

    public String getIdentityYear(String identity) {
        return identity.substring(6, 10);
    }

    public String getIdentityMonth(String identity) {
        return identity.substring(10, 12);
    }

    public String getIdentityDay(String identity) {
        return identity.substring(12, 14);
    }

    public String getIdentitySequence(String identity) {
        return identity.substring(14, 17);
    }

    public String getIdentityCheck(String identity) {
        return identity.substring(17, 18);
    }

    public File getIdentityBelongFile(String identity) {
        File parent = new File("C:\\Development\\Cache\\identitys", getIdentityArea(identity));
        return new File(parent, getIdentityYear(identity));
    }

}
