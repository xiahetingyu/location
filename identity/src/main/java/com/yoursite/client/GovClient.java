package com.yoursite.client;

import com.dtflys.forest.annotation.Get;

/**
 * @description
 * @author: Yue
 * @create: 2020.11.17 21:26
 **/
public interface GovClient {

    /**
     * 去读
     *
     * @return 啊
     */
    @Get(url = "https://user.www.gov.cn/user/regidcard")
    String regidcard();

}
