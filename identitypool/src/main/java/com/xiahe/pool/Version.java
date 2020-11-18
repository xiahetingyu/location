package com.xiahe.pool;

import com.alibaba.fastjson.JSONObject;
import com.xiahe.controller.IdentityGovController;
import com.xiahe.entity.Identity;
import com.xiahe.entity.ProxyPackage;
import com.xiahe.tools.FileTools;
import com.xiahe.tools.NetworkTools;
import com.xiahe.tools.RsaTools;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @author: Yue
 * @create: 2020.01.16 23:16
 **/
@Component
@Scope("prototype")
public class Version implements Runnable {
    public static boolean show;

    private boolean flag = true;
    private Proxy proxy;
    private int count;


    public void end() {
        flag = false;
    }

    private Identity identityGov;

    @Override
    public void run() {
        //等最后的数据处理完毕再退出
        while (flag || identityGov != null) {
            try {
                if (proxy == null) {
                    proxy = getProxy();
                }

                if (identityGov == null) {
                    identityGov = getIdentityGov();
                }

                //如果没有下一个任务了就退出
                if (identityGov.getId() == -1) {
                    System.out.println(IdentityGovController.versions.remove(this) ? "线程已结束" : "线程结束异常");
                    return;
                }

                //验证身份
                ver(identityGov, proxy);
                identityGov = null;
                count = 0;
            } catch (Exception e) {
                e.printStackTrace();
                if (count++ == 2) {
                    proxy = null;
                    count = 0;
                }
            }
        }
        System.out.println(IdentityGovController.versions.remove(this) ? "线程已结束" : "线程结束异常");
    }

    private static final String gov_url = "https://user.www.gov.cn/user/regidcarduser";

    /**
     * 开始验证
     */
    public void ver(Identity identityGov, Proxy proxy) throws Exception {

        //如果是没有验证过的就开始验证
        if (identityGov.getTruly() == null) {
            //加密用户名
            String username = RsaTools.encrypt("赵晴", publicKey);
            username = replace(username);

            //加密身份信息
            String idcard = RsaTools.encrypt(identityGov.getNum(), publicKey);
            idcard = replace(idcard);

            //加密密码
            String password = RsaTools.encrypt("1qazxsw2", publicKey);
            password = replace(password);

            //拼装参数
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("username=").append(username).append("&");
            stringBuilder.append("idcard=").append(idcard).append("&");
            stringBuilder.append("password=").append(password);

            if (show) {
                System.out.println("给出的参数为：" + stringBuilder.toString());
            }

            String network = NetworkTools.networkForString(request, proxy, gov_url, "POST", "UTF-8", stringBuilder.toString());
            System.out.println(network);
            identityGov.setMessage(network);
            identityGov.setTruly(!network.contains("regSavebyidcard_idcard_fail"));

            //更新参数
            setIdentityGov(identityGov);
        }

    }

    //替换特殊符号
    private static String replace(String source) {
        return source.replace("+", "%2B").replace("/", "%2F").replace("=", "%3D");
    }

    private int mode;
    private static String[] urls = {"http://zhaoqing521.date:31314/proxypool/proxy",
            "http://zhaoqing.date:31314/proxypool/proxy", "http://aliyunpan.club:31314/proxypool/proxy"};

    //获取代理
    public Proxy getProxy() throws Exception {
        try {
            String network = NetworkTools.networkForString(null, null, urls[mode++ % urls.length]);
            System.out.println("获取到的代理：" + network);
            ProxyPackage proxyPackage = new ProxyPackage();
            JSONObject jsonObject = JSONObject.parseObject(network);
            proxyPackage.setHost(jsonObject.getString("ip"));
            proxyPackage.setPort(jsonObject.getIntValue("port"));
            return proxyPackage.getProxy();
        } catch (Exception e) {
            e.printStackTrace();
            //请求代理失败了就休眠五分钟
            if (e.getMessage().contains("timed out")) {
                System.out.println(Thread.currentThread().getName() + "连接超时，休眠五分钟");
                Thread.sleep(300000);
            }
            throw e;
        }
    }

    private String get_url = "http://zhaoqing.date:11314/identity/select";

    public Identity getIdentityGov() throws Exception {
        String network = NetworkTools.networkForString(null, null, get_url);
        if (show) {
            System.out.println("获取到的身份信息：" + network);
        }
        return JSONObject.parseObject(network, Identity.class);
    }


    private static Map<String, String> parameter = new HashMap<>();

    private static String publicKey;

    private static Map<String, String> request = new HashMap<>();

    static {
        parameter.put("Content-Type", "application/json");
        publicKey = FileTools.toString(Version.class.getResourceAsStream("/tools/publickey.txt"));
        System.out.println(FileTools.toString(Version.class.getResourceAsStream("/tools/browser.txt")));
        System.out.println(publicKey);
        String[] split = FileTools.toString(Version.class.getResourceAsStream("/tools/browser.txt")).split("\n");
        for (int i = 0; i < split.length; i += 2) {
            request.put(split[i], split[i + 1]);
        }
    }

    private String set_url = "http://zhaoqing.date:11314/identity/update";

    public void setIdentityGov(Identity identityGov) throws Exception {
        String jsonString = JSONObject.toJSONString(identityGov);
        String network = NetworkTools.networkForString(parameter, null, set_url, "POST", "UTF-8", jsonString);
        System.out.println("更新了一条数据：" + jsonString + " 返回：" + network);
    }

}
