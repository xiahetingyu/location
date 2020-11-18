package com.xiahe;

import com.xiahe.entity.ProxyInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * @description
 * @author: Yue
 * @create: 2020.11.01 09:49
 **/
public class Main {

    public static void main(String[] args) throws Exception {
        ProxyInfo proxyInfo = new ProxyInfo();
        Class.forName("org.mariadb.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/datapool", "root", "997304393");
        PreparedStatement preparedStatement = connection.prepareStatement("insert into proxy_info(ip, port) value(?,?)");
        preparedStatement.setString(1, "192.168.0.1");
        preparedStatement.setInt(2, 3306);
        int executeUpdate = preparedStatement.executeUpdate();
        System.out.println(executeUpdate);
        preparedStatement.close();
        connection.close();
    }

}
