package com.xiahe.pool;

import com.alibaba.fastjson.JSONObject;
import com.xiahe.controller.PhoneController;
import com.xiahe.entity.Phone;
import com.xiahe.entity.ProxyPackage;
import com.xiahe.repository.PhoneRepository;
import com.xiahe.tools.FileTools;
import com.xiahe.tools.NetworkTools;
import org.springframework.beans.factory.annotation.Autowired;
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
    public static boolean show = true;

    private boolean flag = true;
    private Proxy proxy;
    private int count;


    public void end() {
        flag = false;
    }

    private Phone phone;

    @Override
    public void run() {
        //等最后的数据处理完毕再退出
        while (flag || phone != null) {
            try {
                if (proxy == null) {
                    proxy = getProxy();
                }

                if (phone == null) {
                    phone = getIdentityGov();
                }

                //如果没有下一个任务了就退出
                if (phone.getId() == -1) {
                    System.out.println(PhoneController.versions.remove(this) ? "线程已结束" : "线程结束异常");
                    return;
                }

                //验证身份
                ver(phone, proxy);
                phone = null;
                count = 0;
            } catch (Exception e) {
                e.printStackTrace();
                if (count++ == 2) {
                    proxy = null;
                    count = 0;
                    System.out.println("更换代理！！！！");
                }
            }
        }
        System.out.println(PhoneController.versions.remove(this) ? "线程已结束" : "线程结束异常");
    }

    private static final String gov_url = "https://kyfw.12306.cn/otn/regist/getRandCode";

    /**
     * 开始验证
     */
    public void ver(Phone phone, Proxy proxy) throws Exception {
        //如果是没有验证过的就开始验证
        if (phone.getTruly() == null) {

            //拼装参数
            String temp = "loginUserDTO.user_name=a472834782abc&userDTO.password=1qazxsw2&confirmPassWord=1qazxsw2&loginUserDTO.id_type_code=1&idTypeRadio=1&loginUserDTO.name=%E5%BC%A0%E4%BC%9F&loginUserDTO.id_no=411627199001010034&loginUserDTO.GAT_valid_date_end=&userDTO.born_date=&userDTO.country_code=CN&userDTO.email=&passenger_type=1&studentInfoDTO.province_code=11&studentInfoDTO.school_code=&studentInfoDTO.school_name=%E7%AE%80%E7%A0%81%2F%E6%B1%89%E5%AD%97&studentInfoDTO.department=&studentInfoDTO.school_class=&studentInfoDTO.student_no=&studentInfoDTO.school_system=1&studentInfoDTO.enter_year=2020&studentInfoDTO.preference_card_no=&studentInfoDTO.preference_from_station_name=%E7%AE%80%E7%A0%81%2F%E6%B1%89%E5%AD%97&studentInfoDTO.preference_from_station_code=&studentInfoDTO.preference_to_station_name=%E7%AE%80%E7%A0%81%2F%E6%B1%89%E5%AD%97&studentInfoDTO.preference_to_station_code=&userDTO.mobile_no=";
            temp += phone.getNum();

            if (show) {
                System.out.println("给出的参数为：" + temp);
            }

            String network = NetworkTools.networkForString(request, proxy, gov_url, "POST", "UTF-8", temp);
            System.out.println(network);


            JSONObject jsonObject = JSONObject.parseObject(network);

            jsonObject = jsonObject.getJSONObject("data");

            phone.setMessage(jsonObject.toJSONString());

            phone.setTruly(phone.getMessage().contains("请使用原账号登录"));

            //更新参数
            setIdentityGov(phone);
        }

    }

    private int mode;
    private static String[] urls = {"http://zhaoqing521.date:31314/proxypool/proxy",
            "http://zhaoqing.date:31314/proxypool/proxy", "http://aliyunpan.club:31314/proxypool/proxy"};

    //获取代理
    public Proxy getProxy() throws Exception {
        try {
            System.out.println("正在获取代理");
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

    public synchronized Phone getIdentityGov() throws Exception {
        if (PhoneController.phones.empty()) {
            Phone phone = new Phone();
            phone.setId(-1);
            return phone;
        }
        return PhoneController.phones.pop();
    }


    private static Map<String, String> request = new HashMap<>();

    static {
        System.out.println(FileTools.toString(Version.class.getResourceAsStream("/tools/browser.txt")));
        String[] split = FileTools.toString(Version.class.getResourceAsStream("/tools/browser.txt")).split("\n");
        for (int i = 0; i < split.length; i += 2) {
            request.put(split[i], split[i + 1]);
        }
    }

    @Autowired
    private PhoneRepository phoneRepository;

    public void setIdentityGov(Phone phone) throws Exception {
        System.out.println("更新：" + phone);
        phoneRepository.saveAndFlush(phone);
    }

}
